export class Product{

    constructor(
        public name: string,
        public description: string,
        public price:number,
        public storeID:string,
        public categoria:string,
        public amount:number,
        public id?:string,
    ){}

}

export enum Categoria {
  OPCION1 = 'SALUD',
  OPCION2 = 'CONSTRUCCION'
}
