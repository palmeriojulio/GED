import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-modal-exibir',
  templateUrl: './modal-exibir.component.html',
  styleUrls: ['./modal-exibir.component.scss']
})
export class ModalExibirComponent implements OnInit {

  dados: any;

  pdfSrc: any;

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    public dialogRef: MatDialogRef<ModalExibirComponent>,
  ) { }

  ngOnInit(): void {
    this.dados = this.data;
    this.converterByteInUint8Array(this.dados);
  }

  public converterByteInUint8Array(dados: any) {
    let TYPED_ARRAY = new Uint8Array(dados.arquivo.dados);
    this.pdfSrc = TYPED_ARRAY;
  }

  public fecharModal(): void {
    this.dialogRef.close(null);
  }

}
