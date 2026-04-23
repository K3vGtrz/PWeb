import axios from "axios";

// URL base para las operaciones CRUD de evaluación
const URL_BASE = "http://localhost:8080/evaluacion";

// Clase que encapsula los métodos para operar con evaluaciones en el servidor
class evaluacionService {

    // Método para obtener todas las evaluaciones
    findAll() {
        return axios.get(URL_BASE);
    }

    // Método para obtener la evaluación por su ID (Agregamos ": number")
    findById(idEvaluacion: number) {
        return axios.get(URL_BASE + '/' + idEvaluacion);
    }

    // Método para crear una nueva evaluación (Agregamos ": object")
    create(evaluacion: object) {
        return axios.post(URL_BASE, evaluacion);
    }

    // Método para actualizar una evaluación existente (Agregamos los tipos a ambos)
    update(idEvaluacion: number, evaluacion: object) {
        return axios.put(URL_BASE + '/' + idEvaluacion, evaluacion);
    }

    // Método para eliminar una evaluación (Agregamos ": number")
    delete(idEvaluacion: number) {
        return axios.delete(URL_BASE + '/' + idEvaluacion);
    }
}

export default new evaluacionService;

