import { Component, Inject, Injectable, OnInit } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatPaginatorIntl } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { Subject } from 'rxjs';
import { PessoaService } from '../pessoa.service';
import { saveAs as importedSaveAs } from "file-saver";
import * as FileSaver from 'file-saver';
import { ModalExibirComponent } from '../modal-exibir/modal-exibir.component';


@Injectable()
export class MyCustomPaginatorIntl implements MatPaginatorIntl {
  changes = new Subject<void>();

  // For internationalization, the `$localize` function from
  // the `@angular/localize` package can be used.
  firstPageLabel = $localize`Primera página`;
  itemsPerPageLabel = $localize`Itens por página:`;
  lastPageLabel = $localize`Última página`;

  // You can set labels to an arbitrary string too, or dynamically compute
  // it through other third-party internationalization libraries.
  nextPageLabel = 'Próxima';
  previousPageLabel = 'Anterior';

  getRangeLabel(page: number, pageSize: number, length: number): string {
    if (length === 0) {
      return $localize`Página 1 de 1`;
    }
    const amountPages = Math.ceil(length / pageSize);
    return $localize`Página ${page + 1} de ${amountPages}`;
  }
}

/**
 * @title Paginator internationalization
 */

@Component({
  selector: 'app-modal-anexos',
  templateUrl: './modal-anexos.component.html',
  styleUrls: ['./modal-anexos.component.scss']
})
export class ModalAnexosComponent implements OnInit {

  dados: any;
  public carregando: boolean = false;
  public modalExibir: any;

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    public dialogRef: MatDialogRef<ModalAnexosComponent>,
    private pessoaService: PessoaService,
    public dialog: MatDialog
  ) { }

  ngOnInit(): void {
    this.dados = this.data;    
  }

  exibirAnexo(event: any) {
    this.carregando = true;
    let params = { nomeArquivo: event.caminhoArchive}
    this.pessoaService.gerarDocumentoVisualizar(params).subscribe((data: any) => {
      if(data != null) {
        //this.executaDownload(data);
        //var file = new File(data.dados, data.nome, {type: "text/plain;charset=utf-8"});
        //importedSaveAs(data.dados, `receituarioPaciente.pdf`);
        //FileSaver.saveAs(file);

        //var file = new Blob([data.dados], { type: 'application/pdf' });
        //var fileURL = URL.createObjectURL(file);
        //window.open(fileURL);
        this.exibirArquivo(data);
        this.carregando = false;
      } else {
        this.carregando = false;
      }
    });
  }

  exibirArquivo(arquivo: any){
    
    this.modalExibir = this.dialog.open(ModalExibirComponent, {
      width: '80%',
      height: '90%',
      data: {arquivo: arquivo},
      disableClose: true
    });
  }
  

  executaDownload(data: any) {
    if (!data.dados) {
      return;
    }

    document.body.setAttribute('style', 'cursor: progress');
    const byteArray = new Uint8Array(data.dados);
    const blob = new Blob([byteArray], { type: 'application/octet-stream' });
    const url = URL.createObjectURL(blob);
    const a = document.createElement('a');
    document.body.appendChild(a);
    a.setAttribute('style', 'display: none');
    a.href = url;
    a.download = data.nome ? data.nome + '.pdf' : 'relatorio-padrao.pdf';
    a.click();
    window.URL.revokeObjectURL(url);
    a.remove();
    document.body.setAttribute('style', 'cursor: auto');
        
  }

  public fecharModal(): void {
    this.dialogRef.close(null);
  }
}
