import axios from "axios";
const URL_BASE = "http://localhost:8080/detalleEvaluacion";
class detalleEvaluacionService{
    //obtener todos los alumnos
    findAll(){
        return axios.get(URL_BASE);
    }

    //obtener por id
    findById(id_DetalleEvaluacion: number){
        return axios.get(URL_BASE + '/' + id_DetalleEvaluacion);
    }

    //crear un nuevo alumno
    create(DetalleEvaluacion: object){
        return axios.post(URL_BASE, DetalleEvaluacion);
    }

    //actualizar alumno
    update(id_DetalleEvaluacion: number, DetalleEvaluacion: object){
        return axios.put(URL_BASE + "/" + id_DetalleEvaluacion), DetalleEvaluacion;
    }

    //eliminar
    delete(id_DetalleEvaluacion: number){
        return axios.delete(URL_BASE + "/" + id_DetalleEvaluacion);
    }
}

export default new detalleEvaluacionService();