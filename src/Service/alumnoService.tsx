import axios from 'axios';

const URL_BASE = 'http://localhost:8080/alumno';

class alumnoService {
    findAll(){
        return axios.get(URL_BASE);
    }
    create(alumno: object){
        return axios.post(URL_BASE, alumno);
    }
    update(numControl: string, alumno: object){
        return axios.put(URL_BASE + '/' + numControl, alumno);
    }
    delete(numControl: string){
        return axios.delete(URL_BASE + '/' + numControl);
    }
}

export default new alumnoService();