import { Product } from "./Product";

export class Sale{

    constructor(
        public userId:string,
        public date:Date,
        public total:number,
        public products:Product[],
        public id?:string,
    ){}

}