import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavbarComponent } from './core/navbar/navbar.component';
import { ConsultarPessoaComponent, MyCustomPaginatorIntl } from './consultar-pessoa/consultar-pessoa.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatTableModule } from '@angular/material/table';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import { MatOptionModule } from '@angular/material/core';
import { MatPaginatorIntl, MatPaginatorModule } from '@angular/material/paginator';
import { MatButtonModule } from '@angular/material/button';
import { MatSortModule } from '@angular/material/sort';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatCardModule } from '@angular/material/card';
import { MatInputModule } from '@angular/material/input';
import { MatDialogModule } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { FormsModule } from '@angular/forms';
import { PessoaService } from './consultar-pessoa/pessoa.service';
import { HttpClientModule } from '@angular/common/http';
import { ModalAnexosComponent } from './consultar-pessoa/modal-anexos/modal-anexos.component';
import { TextMaskModule } from 'angular2-text-mask';
import { NgxExtendedPdfViewerModule } from 'ngx-extended-pdf-viewer';
import { PdfViewerModule } from 'ng2-pdf-viewer';
import { ModalExibirComponent } from './consultar-pessoa/modal-exibir/modal-exibir.component';

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    ConsultarPessoaComponent,
    ModalAnexosComponent,
    ModalExibirComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatTableModule,
    MatFormFieldModule,
    MatSelectModule,
    MatOptionModule,
    MatPaginatorModule,
    MatButtonModule,
    MatSortModule,
    MatTooltipModule,
    MatToolbarModule,
    MatCardModule,
    MatInputModule,
    MatDialogModule,
    MatProgressSpinnerModule,
    MatIconModule,
    MatProgressBarModule,
    MatSnackBarModule,
    TextMaskModule,
    FormsModule,
    HttpClientModule,
    NgxExtendedPdfViewerModule,
    PdfViewerModule
  ],
  providers: [ 
    PessoaService,
    {provide: MatPaginatorIntl, useClass: MyCustomPaginatorIntl}
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
