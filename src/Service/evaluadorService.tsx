import axios from "axios";

// URL base para las operaciones CRUD de evaluadores
const URL_BASE = "http://localhost:8080/evaluador";

// Clase que encapsula los métodos para operar con evaluadores en el servidor
class evaluadorService {

    // Método para obtener todos los evaluadores
    findAll() {
        return axios.get(URL_BASE);
    }

    // Método para obtener el evaluador por su ID
    findById(idEvaluador: number) {
        return axios.get(URL_BASE + '/' + idEvaluador);
    }

    // Método para crear un nuevo evaluador
    create(evaluador: object) {
        return axios.post(URL_BASE, evaluador);
    }

    // Método para actualizar un evaluador existente
    update(idEvaluador: number, evaluador: object) {
        return axios.put(URL_BASE + '/' + idEvaluador, evaluador);
    }

    // Método para eliminar un evaluador
    delete(idEvaluador: number) {
        return axios.delete(URL_BASE + '/' + idEvaluador);
    }
}

// Se exporta una instancia de la clase EvaluadorService
export default new evaluadorService();