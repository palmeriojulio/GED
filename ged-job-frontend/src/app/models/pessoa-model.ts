import { Documento } from "./documento-model";

export class Pessoa {
    id?: number;
    nome?: string;
    cpf?: string;
    logradouro?: string;
    documentos?: Array<Documento>;
}