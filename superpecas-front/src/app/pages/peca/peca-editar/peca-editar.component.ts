import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { MatCard, MatCardContent, MatCardHeader, MatCardSubtitle, MatCardTitle } from "@angular/material/card";
import { MatFormField, MatLabel } from "@angular/material/form-field";
import { MatButton } from "@angular/material/button";
import { MatInput } from "@angular/material/input";
import { PecaService } from "../../../services/peca.service";
import { CarroService } from "../../../services/carro.service";
import { ICarro } from "../../../Types/ICarro";
import {MatOption} from "@angular/material/core";
import {MatSelect} from "@angular/material/select";

@Component({
  selector: 'app-peca-editar',
  templateUrl: './peca-editar.component.html',
  styleUrls: ['./peca-editar.component.css'],
  standalone: true,
  imports: [
    MatCard,
    MatCardTitle,
    MatCardHeader,
    MatCardContent,
    ReactiveFormsModule,
    MatFormField,
    MatButton,
    MatInput,
    MatLabel,
    MatCardSubtitle,
    MatOption,
    MatSelect
  ],
})
export class PecaEditarComponent implements OnInit {

  carros: ICarro[] = [];
  pecaForm!: FormGroup;
  pecaId!: number;

  constructor(
    private fb: FormBuilder,
    private pecaService: PecaService,
    private carroService: CarroService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.pecaId = this.route.snapshot.params['id'];
    this.createForm();
    this.getNames();

    this.pecaService.getPecaById(this.pecaId).subscribe(peca => {
      this.pecaForm.patchValue({
        nome: peca.nome,
        fabricante: peca.fabricante,
        descricao: peca.descricao,
        serial_number: peca.serial_number,
        carro: peca.carro?.id || null
      });
    });
  }

  createForm() {
    this.pecaForm = this.fb.group({
      nome: [''],
      fabricante: [''],
      descricao: [''],
      serial_number: [''],
      carro: ['']
    });
  }

  getNames() {
    this.carroService.getAllCarros().subscribe(response => {
      this.carros = response;
    });
  }

  onCancel() {
    this.router.navigate(['/pages/peca/listar']); // Navegar de volta para a listagem
  }

  onSubmit() {
    const carroIdSelecionado = this.pecaForm.value.carro;
    const carroSelecionado = this.carros.find(carro => carro.id === carroIdSelecionado);

    if (carroSelecionado) {
      this.pecaForm.patchValue({ carro: carroSelecionado });
    }

    this.pecaService.editPeca(this.pecaId, this.pecaForm.value).subscribe(() => {
      this.router.navigate(['/pages/peca/listar']);
    });
  }

  limparDados() {
    this.pecaForm.reset();
  }
}
