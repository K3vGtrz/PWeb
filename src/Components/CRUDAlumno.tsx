import React, {useState, useEffect, useRef} from 'react';
import {classNames} from 'primereact/utils';
import {DataTable} from 'primereact/datatable';
import {Column} from 'primereact/column';
import {Toast} from 'primereact/toast';
import {Button} from 'primereact/button';
import {Toolbar} from 'primereact/toolbar';
import {IconField} from 'primereact/iconfield';
import {InputIcon} from 'primereact/inputicon';
import {Dialog} from 'primereact/dialog';
import {InputText} from 'primereact/inputtext';
import AlumnoService from '../Service/alumnoService.tsx'

interface Alumno {
    numControl: string;
    nombre: string;
    semestre: number;
    grupo: string;
}

interface detalleEvaluacion {
    id_DetalleEvaluacion: number;
    puntaje: number;
    status: string;
}

function CRUDAlumno() {
    const emptyAlumno: Alumno = {
        numControl: '',
        nombre: '',
        semestre: 0,
        grupo: ''
    };
    //Listado de detalle_evaluacion
    const [listaDetalleEva, setListaDetalleEva] = useState<detalleEvaluacion[]>([]);
    const [alumnos, setAlumnos] = useState<Alumno[]>([]);
    const [alumno, setAlumno] = useState<Alumno>(emptyAlumno);
    const [alumnoDialog, setAlumnoDialog] = useState<boolean>(false);
    const [deleteAlumno, setDeleteAlumno] = useState<boolean>(false);
    //listado del alumno
    const [alumnoListado, setAlumnoListado] = useState<boolean>(false);
    const [submitted, setSubmitted] = useState<boolean>(false);
    const [globalFilter, setGlobalFilter] = useState<string>('');
    const toast = useRef<Toast>(null);
    const dt = useRef<DataTable<Alumno[]>>(null);

    useEffect(() => {
        AlumnoService.findAll().then((response) => setAlumnos(response.data));
    }, []);

    const openNew = () => {
        setAlumno(emptyAlumno);
        setSubmitted(false);
        setAlumnoDialog(true);
    };
    //Oculta ellistado de mascotas
    const hideListadoDialog = () => {
        setSubmitted(false);
        setAlumnoListado(false);
    };

    const hideDialog = () => {
        setSubmitted(false);
        setAlumnoDialog(false);
    };

    const hideDeleteObjetoDialog = () => {
        setDeleteAlumno(false);
    };

    const saveAlumno = async () => {
        setSubmitted(true);
        if (alumno.nombre.trim()) {
            const _alumnos = [...alumnos];
            const _alumno = {...alumno};

            if (alumno.numControl) {
                AlumnoService.update(alumno.numControl, alumno);
                const index = findIndexById(alumno.numControl);
                _alumnos[index] = _alumno;
                toast.current?.show({
                    severity: 'success',
                    summary: "Éxito",
                    detail: "Alumno actualizado",
                    life: 3000
                });
            } else {
                _alumno.numControl = await getIdNumControl(_alumno);
                _alumnos.push(_alumno);
                toast.current?.show({
                    severity: 'success',
                    summary: "Éxito",
                    detail: "Alumno creado",
                    life: 3000
                });
            }
            setAlumnos(_alumnos);
            setAlumnoDialog(false);
            setAlumno(emptyAlumno);
        }
    };

    const getNumControl = async (_alumno: Alumno) => {
        let numControl: '';
        const newAlumno = {
            nombre: _alumno.nombre,
            semestre: _alumno.semestre,
            grupo: _alumno.grupo,
        };
        await AlumnoService.create(newAlumno).then((response) => {
            numControl = response.data.numControl;
        }).catch(error => {
            console.log(error);
        });
        return numControl;
    };

    //Listar detalleEvaluacion
    const listarDetalleEva = (alumno: Alumno) => {
        setAlumno({...alumno});
        AlumnoService.findById(alumno.numControl).then((response) => {
            setListaDetalleEva(response.data.alumnos);
        }).catch(error => {
            console.log(error);
        });
        setAlumnoListado(true);
    };

    const editAlumno = (alumno: Alumno) => {
        setAlumno({...alumno});
        setAlumno(true);
    };

    const confirmDeleteAlumno = (alumno: Alumno) => {
        setAlumno(alumno);
        setDeleteAlumno(true);
    }

    const deleteAlumno = () => {
        const _alumnos = alumno.filter((val) => val.numControl !== alumno.numControl);
        AlumnoService.delete(alumno.numControl);
        setAlumnos(_alumnos);
        setDeleteAlumno(false);
        setAlumno(emptyAlumno);
        toast.current?.show({
            severity: 'success',
            summary: 'Resultado',
            detail: "Alumno eliminado",
            life: 3000
        });
    };

    const findIndexById = (numControl: string) => {
        let index = -1;
        for (let i = 0; i < alumnos.length; i++) {
            if (alumnos[i].numControl === numControl) {
                index = i;
                break;
            }
        }
        return index
    };

    const exportCSV = () => {
        dt.current?.exportCSV();
    };

    const onInputChange = (e: React.ChangeEvent<HTMLInputElement>,
                           numCampo: number) => {
        const val = (e.target && e.target.value) || '';
        const _alumno = {...alumno};
        switch (numCampo) {
            case 1:
                _alumno.nombre = val;
                break;
            case 2:
                _alumno.semestre = val;
                break;
            case 3:
                _alumno.grupo = val;
                break;
        }
        setAlumno(_alumno);
    };

    const leftToolbarTemplate = () => {
        return (
            <div className="flex flex-wrap gap-2">
                <Button label="Nuevo" icon="pi pi-plus" severity="success" onClick={openNew}/>
            </div>
        );
    };

    const rightToolbarTemplate = () => {
        return <Button label="Exportar" icon="pi pi-upload" className="p-button-help" onClick={exportCSV}/>
    };

    const actionBodyTemplate = (rowData: Alumno) => {
        return (
            <React.Fragment>
                <Button icon="pi pi-prime" style={{color: 'green'}} rounded outlined className="mr2" onClick{() =>
                    listarDetalleEva(rowData)}/>
                <Button icon="pi pi-pencil" rounded outlined className="mr-2" onClcik={() =>
                    editAlumno(rowData)}/>
                <Button icon="pi pi-trash" rounded outlined severity="danger" onClick={() =>
                    confirmDeleteAlumno(rowData)}/>
            </React.Fragment>
        );
    };

    const header = (
        <div className="flex flex-wrap gap-2 align-item-center justify-content-netween">
            <h4 className="m-0">Gestion de detalle evaluacion</h4>
            <IconField iconPosition="left">
                <InputIcon className="pi pi-search"/>
                <InputText type="search" placeholder="Buscar..." onInput={(e) => {
                    const target = e.target as HTMLInputElement;
                    setGlobalFilter(target.value);
                }}/>
            </IconField>
        </div>
    );

    const objetoDialogFooter = (
        <React.Fragment>
            <Button label="Cancelar" icon="pi pi-times" outlined onClick={hideDialog}/>
            <Button label="Guardar" icon="pi pi-check" onClick={saveAlumno}/>
        </React.Fragment>
    );

    const deleteObjetoFlooter = (
        <React.Fragment>
            <Button label="No" icon="pi pi-times" outlined onClick={hideDeleteObjetoDialog}/>
            <Button label="Si" icon="pi pi-check" severity="danger" onClick={deleteAlumno}/>
        </React.Fragment>
    );
    
    return (
        <div>
            <Toast ref={toast}/>
            <div className="card">
                <Toolbar className="mb-4" left={leftToolbarTemplate}
                         right={rightToolbarTemplate}></Toolbar>
                <DataTable ref={dt} value={alumnos} dataKey="numControl" paginator rows={10} rowsPerPageOptions={[5,10,25]}
                           paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink CurrentPageReport
                           RowsPerPageDropdown"
                           currentPageReportTemplate="Mostrando de {fisrt} a {last} de {totalRecords} evaluaciones"
                           globalFilter={globalFilter} header={header}>
                    <Column field="numControl" header="NumControl" sortable style={{minWidth: '5rem'}}></Column>
                    <Column field="nombre" header="Nombre" sortable style={{minWidth: '10rem'}}></Column>
                    <Column field="semestre" header="Semestre" sortable style={{minWidth: '10rem'}}></Column>
                    <Column field="grupo" header="Grupo" sortable style={{minWidth: '10rem'}}></Column>
                    <Column body={actionBodyTemplate} exportable={false} style={{minWidth: '12rem'}}></Column>
                </DataTable>
            </div>

            <Dialog visible={alumnoDialog} style={{width: '32rem'}} breakpoints={{'960px': '75vw', '641px': '90vw'}}
                    header="Detalle del alumno" modal className="p-fluid" footer={objetoDialogFooter}
                    onHide={hideDialog}>
                <div className="field">
                    <label htmlFor="nombre" className="font-blod">
                        Nombre
                        </label>
                    <InputText id="nombre" value={alumno.nombre} onChange={(e) => onInputChange(e,1)} required autoFocus
                           className={classNames({'p-invalid': submitted && !alumno.nombre})}/>
                    {submitted && !alumno.nombre && <small className="p-error">El nombre es requerido</small>}
                </div>

            </Dialog>
        </div>
    )
}