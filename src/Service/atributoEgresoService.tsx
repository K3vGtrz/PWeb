import axios from "axios";
const URL_BASE = "http://localhost:8080/atributoEgreso";
class atributoEgresoService{
    //obtener todos los alumnos
    findAll(){
        return axios.get(URL_BASE);
    }

    //obtener por id
    findById(id_Atributo: number){
        return axios.get(URL_BASE + '/' + id_Atributo);
    }

    //crear un nuevo alumno
    create(AtributoEgreso: object){
        return axios.post(URL_BASE, AtributoEgreso);
    }

    //actualizar alumno
    update(id_Atributo: number, AtributoEgreso: object){
        return axios.put(URL_BASE + "/" + id_Atributo, AtributoEgreso);
    }

    //eliminar
    delete(id_Atributo: number){
        return axios.delete(URL_BASE + "/" + id_Atributo);
    }
}

export default new atributoEgresoService();