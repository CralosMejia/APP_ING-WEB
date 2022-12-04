import { User } from "./User.model";

export class Store{

    constructor(
        public name: string,
        public address: string,
        public owner:string,
        public id?:string,
    ){}

}