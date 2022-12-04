export class Product{

    constructor(
        public name: string,
        public description: string,
        public price:number,
        public storeID:string,
        public amount:number,
        public id?:string,
    ){}

}