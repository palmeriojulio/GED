import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { GED_API } from 'app.api';

@Injectable({
  providedIn: 'root'
})
export class PessoaService {

  constructor(private http: HttpClient) { }

  listarPessoas() {
    return this.http.get(`${GED_API}pessoa`);
  }

  findPessoaByCpf(cpf: string) {
    return this.http.get(`${GED_API}pessoa/findPessoaByCpf/${cpf}`);
  }

  listPessoaByNome(nome: string) {
    return this.http.get(`${GED_API}pessoa/listPessoaByNome/${nome}`);
  }

  listPessoaByEndereco(endereco: string) {
    return this.http.get(`${GED_API}pessoa/listPessoaByEndereco/${endereco}`);
  }

  gerarDocumentoVisualizar(params:any) {
    return this.http.get(`${GED_API}documento/gerarDocumentoVisualizar`,  { params});
  }
}
