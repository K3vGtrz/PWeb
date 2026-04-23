import axios from "axios";
const URL_BASE = "http://localhost:8080/atributoEvaluar";
class AtributoEvaluarService{
    //obtener todos los alumnos
    findAll(){
        return axios.get(URL_BASE);
    }

    //obtener por id
    findById(id_AtributoEvaluar: number){
        return axios.get(URL_BASE + '/' + id_AtributoEvaluar);
    }

    //crear un nuevo alumno
    create(AtributoEvaluar: object){
        return axios.post(URL_BASE, AtributoEvaluar);
    }

    //actualizar alumno
    update(id_AtributoEvaluar: number, AtributoEvaluar: object){
        return axios.put(URL_BASE + "/" + id_AtributoEvaluar,AtributoEvaluar);
    }

    //eliminar
    delete(id_AtributoEvaluar: number){
        return axios.delete(URL_BASE + "/" + id_AtributoEvaluar);
    }
}

export default new AtributoEvaluarService();