import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ConsultarPessoaComponent } from './consultar-pessoa/consultar-pessoa.component';

const routes: Routes = [
  {
    path: '',
    component: ConsultarPessoaComponent,   
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
