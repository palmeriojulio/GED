import { AfterViewInit, ChangeDetectorRef, Component, Injectable, NgModule, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator, MatPaginatorIntl, MatPaginatorModule } from '@angular/material/paginator';
import { MatSnackBar, MatSnackBarHorizontalPosition, MatSnackBarVerticalPosition } from '@angular/material/snack-bar';
import { MatTableDataSource} from '@angular/material/table';
import { Subject } from 'rxjs';
import { Pessoa } from '../models/pessoa-model';
import { ModalAnexosComponent } from './modal-anexos/modal-anexos.component';
import { ModalExibirComponent } from './modal-exibir/modal-exibir.component';
import { PessoaService } from './pessoa.service';

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
  selector: 'app-consultar-pessoa',
  templateUrl: './consultar-pessoa.component.html',
  styleUrls: ['./consultar-pessoa.component.scss']
})
export class ConsultarPessoaComponent implements OnInit {

  @ViewChild('paginator') paginator!: MatPaginator;

  public modalAnexos: any;
  public modalExibir: any;

  horizontalPosition: MatSnackBarHorizontalPosition = 'right';
  verticalPosition: MatSnackBarVerticalPosition = 'top';
  durationInSeconds = 5;

  cpfmask = [/\d/, /\d/,/\d/, '.', /\d/, /\d/,/\d/, '.', /\d/, /\d/, /\d/, '-',/\d/,/\d/];

  public tiposConsulta = [
    { valor: "CPF_CNPJ", descricao: "CPF/CNPJ" },
    { valor: "NOME", descricao: "Nome" },
    { valor: "ENDERECO", descricao: "Endereço" }
  ]

  public listaResultados: any[] = [];
  public tipoConsulta: string = '';
  public parametro: string = '';
  public podePesquisar: boolean = true;

  displayedColumns: string[] = ['nome', 'cpf_cnpj', 'logradouro'];
  dataSource!: MatTableDataSource<Pessoa>;

  constructor(
    private pessoaService: PessoaService,
    public dialog: MatDialog,
    private _snackBar: MatSnackBar) { }

  ngOnInit(): void {
    this.listarPessoas();
  }

  consulta() {
    this.validarPesquisa();
      if(this.podePesquisar) {
        if(this.tipoConsulta !== undefined && this.tipoConsulta == 'CPF_CNPJ') {
          this.findPessoaByCpf();
        } else if(this.tipoConsulta !== undefined && this.tipoConsulta == 'NOME') {
          this.listPessoaByNome();
        } else if(this.tipoConsulta !== undefined && this.tipoConsulta == 'ENDERECO') {
          this.listPessoaByEndereco();
        }
      }
  }

  limpar() {
    this.tipoConsulta  = "";
    this.parametro = "";
    this.listarPessoas();
  }

  validarPesquisa() {
    this.podePesquisar = true;
    var numbers = this.parametro.replace(/[^0-9]/g,'')
    if(this.tipoConsulta === undefined || this.tipoConsulta === "")  {
      this.openSnackBar("É necessário informar o Tipo Consulta");
      this.podePesquisar = false;
    }

    if(this.parametro === undefined || this.parametro === "")  {
      this.openSnackBar("É necessário informar o Parâmetro");
      this.podePesquisar = false;
    }

    // if((this.tipoConsulta !== undefined && this.tipoConsulta !== "" && this.tipoConsulta == "CPF") &&
    // (this.parametro !== undefined && this.parametro !== "" && numbers.length < 11)) {
    //   this.openSnackBar("O CPF informado esta incompleto");
    //   this.podePesquisar = false;
    // }
  }

  findPessoaByCpf() {
    this.pessoaService.findPessoaByCpf(this.parametro).subscribe((res:any) => {
      this.dataSource = new MatTableDataSource<Pessoa>();
      this.listaResultados = [];
      this.listaResultados.push(res);
      this.dataSource =  new MatTableDataSource(this.listaResultados);
      this.dataSource.paginator = this.paginator;
    });
  }

  listarPessoas() {
    this.pessoaService.listarPessoas().subscribe((res: any) => {
      this.dataSource =  new MatTableDataSource(res);
      this.dataSource.paginator = this.paginator;
    });
  }

  listPessoaByNome() {
    this.pessoaService.listPessoaByNome(this.parametro).subscribe((res: any) => {
      this.dataSource = new MatTableDataSource<Pessoa>();
      this.dataSource =  new MatTableDataSource(res);
      this.dataSource.paginator = this.paginator;
    });
  }

  listPessoaByEndereco() {
    this.pessoaService.listPessoaByEndereco(this.parametro).subscribe((res: any) => {
      this.dataSource = new MatTableDataSource<Pessoa>();
      this.dataSource =  new MatTableDataSource(res);
      this.dataSource.paginator = this.paginator;
    });
  }

  abrirAnexo(resultado: any) {
    // this.carregando = true;
    resultado.documentos
    let params = { nomeArquivo: resultado.documentos[0].caminhoArchive}
    this.pessoaService.gerarDocumentoVisualizar(params).subscribe((data: any) => {
      if(data != null) {
        this.exibirArquivo(data);
        // this.carregando = false;
      } else {
        // this.carregando = false;
      }
    });
  }

  a(resultado: any){

    this.modalAnexos = this.dialog.open(ModalAnexosComponent, {
      width: '60%',
      height: '80%',
      data: {resultado: resultado},
      disableClose: true
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

  openSnackBar(mensagem: string) {
    this._snackBar.open(mensagem, 'x', {
      horizontalPosition: this.horizontalPosition,
      verticalPosition: this.verticalPosition,
      duration: this.durationInSeconds * 1000
    });
  }
}
