import {Component, OnInit, ViewChild, AfterViewInit} from '@angular/core';
import {MatPaginator} from '@angular/material/paginator';
import {MatTableDataSource} from '@angular/material/table';
import {CarroService} from '../../../services/carro.service';
import {Router} from '@angular/router';
import {AppModule} from "../../../app.module";

@Component({
  selector: 'app-carro-listar-component',
  templateUrl: './carro-listar-component.html',
  styleUrls: ['./carro-listar-component.css']
})
export class CarroListarComponent implements OnInit, AfterViewInit,AppModule {
  displayedColumns: string[] = ['id', 'nome', 'fabricante', 'serial', 'acoes'];
  dataSource = new MatTableDataSource<any>();
  totalItems = 5;
  pageSize = 10;

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  value = ''

  constructor(private carroService: CarroService, private router: Router) {
  }

  ngOnInit() {
    this.loadItemsPage(0, this.pageSize);
  }

  ngAfterViewInit() {
    this.paginator.pageSize = this.pageSize;
    this.paginator.page.subscribe(() => this.loadItemsPage(this.paginator.pageIndex, this.paginator.pageSize));
  }

  loadItemsPage(pageIndex: number, pageSize: number) {
    this.carroService.getCarros(pageIndex, pageSize).subscribe(data => {
      this.dataSource.data = data.content;
      this.totalItems = data.totalElements;
      if (this.paginator) {
        this.paginator.pageIndex = pageIndex;
        this.paginator.length = this.totalItems;
      }
    });
  }
  loadData() {
    this.carroService.getAllCarros().subscribe(data => {
      this.dataSource.data = data;
      this.totalItems = data.length;
    });
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    if (filterValue) {
      this.carroService.getCarrosPorNome(filterValue).subscribe(data => {
        this.dataSource.data = data;
        this.totalItems = data.length;
      });
    } else {
      this.loadData();
    }
  }

  editCarro(carro: any) {
    this.router.navigate(['/pages/carro/editar/', carro.id]);
  }

  deleteCarro(carro: any) {
    this.carroService.deleteCarro(carro.id).subscribe(() => {
      this.loadItemsPage(this.paginator.pageIndex, this.paginator.pageSize);
    });
  }
}
