import React, { useState, useEffect, useRef } from 'react';
import { classNames } from 'primereact/utils';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import { Toast } from 'primereact/toast';
import { Button } from 'primereact/button';
import { Toolbar } from 'primereact/toolbar';
import { IconField } from 'primereact/iconfield';
import { InputIcon } from 'primereact/inputicon';
import { Dialog } from 'primereact/dialog';
import { InputText } from 'primereact/inputtext';

// Asegúrate de que la ruta apunte correctamente a tu servicio de Evaluador
import EvaluadorService from '../Service/evaluadorService';

// Interfaz que modela a la entidad Evaluador
interface Evaluador {
    id_Evaluador: number;
    nombre_Evaluador: string;
    materia: string;
}

export default function CRUDEvaluadorComponent() {
    // Objeto por defecto
    const emptyEvaluador: Evaluador = {
        id_Evaluador: 0,
        nombre_Evaluador: '',
        materia: ''
    };

    // Variables de estado
    const [evaluadores, setEvaluadores] = useState<Evaluador[]>([]);
    const [evaluador, setEvaluador] = useState<Evaluador>(emptyEvaluador);
    const [evaluadorDialog, setEvaluadorDialog] = useState<boolean>(false);
    const [deleteEvaluadorDialog, setDeleteEvaluadorDialog] = useState<boolean>(false);
    const [submitted, setSubmitted] = useState<boolean>(false);
    const [globalFilter, setGlobalFilter] = useState<string>('');

    const toast = useRef<Toast>(null);
    const dt = useRef<DataTable<Evaluador[]>>(null);

    // Cargar los datos al iniciar el componente
    useEffect(() => {
        EvaluadorService.findAll().then((response) => setEvaluadores(response.data));
    }, []);

    const openNew = () => {
        setEvaluador(emptyEvaluador);
        setSubmitted(false);
        setEvaluadorDialog(true);
    };

    const hideDialog = () => {
        setSubmitted(false);
        setEvaluadorDialog(false);
    };

    const hideDeleteEvaluadorDialog = () => {
        setDeleteEvaluadorDialog(false);
    };

    // Guardar o actualizar evaluador
    const saveEvaluador = async () => {
        setSubmitted(true);

        if (evaluador.nombre_Evaluador.trim()) {
            let _evaluadores = [...evaluadores];
            let _evaluador = { ...evaluador };

            if (evaluador.id_Evaluador) {
                // Actualizar
                await EvaluadorService.update(evaluador.id_Evaluador, _evaluador);
                const index = findIndexById(evaluador.id_Evaluador);
                _evaluadores[index] = _evaluador;
                toast.current?.show({ severity: 'success', summary: 'Éxito', detail: 'Registro Actualizado', life: 3000 });
            } else {
                // Crear
                await EvaluadorService.create(_evaluador).then((response) => {
                    _evaluador.id_Evaluador = response.data.id_Evaluador;
                    _evaluadores.push(_evaluador);
                    toast.current?.show({ severity: 'success', summary: 'Éxito', detail: 'Registro Creado', life: 3000 });
                }).catch(error => console.log(error));
            }

            setEvaluadores(_evaluadores);
            setEvaluadorDialog(false);
            setEvaluador(emptyEvaluador);
        }
    };

    const editEvaluador = (evaluador: Evaluador) => {
        setEvaluador({ ...evaluador });
        setEvaluadorDialog(true);
    };

    const confirmDeleteEvaluador = (evaluador: Evaluador) => {
        setEvaluador(evaluador);
        setDeleteEvaluadorDialog(true);
    };

    const deleteEvaluador = async () => {
        await EvaluadorService.delete(evaluador.id_Evaluador);
        let _evaluadores = evaluadores.filter((val) => val.id_Evaluador !== evaluador.id_Evaluador);
        setEvaluadores(_evaluadores);
        setDeleteEvaluadorDialog(false);
        setEvaluador(emptyEvaluador);
        toast.current?.show({ severity: 'success', summary: 'Resultado', detail: 'Registro Eliminado', life: 3000 });
    };

    const findIndexById = (id_Evaluador: number) => {
        let index = -1;
        for (let i = 0; i < evaluadores.length; i++) {
            if (evaluadores[i].id_Evaluador === id_Evaluador) {
                index = i;
                break;
            }
        }
        return index;
    };

    const exportCSV = () => {
        dt.current?.exportCSV();
    };

    const onInputChange = (e: React.ChangeEvent<HTMLInputElement>, name: string) => {
        const val = (e.target && e.target.value) || '';
        let _evaluador = { ...evaluador };
        _evaluador[name] = val;
        setEvaluador(_evaluador);
    };

    // Plantillas de la barra de herramientas
    const leftToolbarTemplate = () => {
        return (
            <div className="flex flex-wrap gap-2">
                <Button label="Nuevo" icon="pi pi-plus" severity="success" onClick={openNew} />
            </div>
        );
    };

    const rightToolbarTemplate = () => {
        return <Button label="Exportar" icon="pi pi-upload" className="p-button-help" onClick={exportCSV} />;
    };

    const actionBodyTemplate = (rowData: Evaluador) => {
        return (
            <React.Fragment>
                <Button icon="pi pi-pencil" rounded outlined className="mr-2" onClick={() => editEvaluador(rowData)} />
                <Button icon="pi pi-trash" rounded outlined severity="danger" onClick={() => confirmDeleteEvaluador(rowData)} />
            </React.Fragment>
        );
    };

    const header = (
        <div className="flex flex-wrap gap-2 align-items-center justify-content-between">
            <h4 className="m-0">Gestión de Evaluadores</h4>
            <IconField iconPosition="left">
                <InputIcon className="pi pi-search" />
                <InputText type="search" placeholder="Buscar..." onInput={(e) => {
                    const target = e.target as HTMLInputElement;
                    setGlobalFilter(target.value);
                }} />
            </IconField>
        </div>
    );

    const evaluadorDialogFooter = (
        <React.Fragment>
            <Button label="Cancelar" icon="pi pi-times" outlined onClick={hideDialog} />
            <Button label="Guardar" icon="pi pi-check" onClick={saveEvaluador} />
        </React.Fragment>
    );

    const deleteEvaluadorDialogFooter = (
        <React.Fragment>
            <Button label="No" icon="pi pi-times" outlined onClick={hideDeleteEvaluadorDialog} />
            <Button label="Sí" icon="pi pi-check" severity="danger" onClick={deleteEvaluador} />
        </React.Fragment>
    );

    return (
        <div>
            <Toast ref={toast} />
            <div className="card">
                <Toolbar className="mb-4" left={leftToolbarTemplate} right={rightToolbarTemplate}></Toolbar>

                <DataTable ref={dt} value={evaluadores} dataKey="id_Evaluador" paginator rows={10} rowsPerPageOptions={[5, 10, 25]}
                           paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink CurrentPageReport RowsPerPageDropdown"
                           currentPageReportTemplate="Mostrando {first} a {last} de {totalRecords} evaluadores" globalFilter={globalFilter} header={header}>

                    <Column field="id_Evaluador" header="ID" sortable style={{ minWidth: '8rem' }}></Column>
                    <Column field="nombre_Evaluador" header="Nombre del Evaluador" sortable style={{ minWidth: '16rem' }}></Column>
                    <Column field="materia" header="Materia" sortable style={{ minWidth: '16rem' }}></Column>
                    <Column body={actionBodyTemplate} exportable={false} style={{ minWidth: '12rem' }}></Column>
                </DataTable>
            </div>

            {/* Diálogo para Crear / Editar Evaluador */}
            <Dialog visible={evaluadorDialog} style={{ width: '32rem' }} breakpoints={{ '960px': '75vw', '641px': '90vw' }} header="Detalle del Evaluador" modal className="p-fluid" footer={evaluadorDialogFooter} onHide={hideDialog}>

                <div className="field">
                    <label htmlFor="nombre_Evaluador" className="font-bold">Nombre</label>
                    <InputText id="nombre_Evaluador" value={evaluador.nombre_Evaluador} onChange={(e) => onInputChange(e, 'nombre_Evaluador')} required autoFocus className={classNames({ 'p-invalid': submitted && !evaluador.nombre_Evaluador })} />
                    {submitted && !evaluador.nombre_Evaluador && <small className="p-error">El nombre es requerido.</small>}
                </div>

                <div className="field mt-3">
                    <label htmlFor="materia" className="font-bold">Materia</label>
                    <InputText id="materia" value={evaluador.materia} onChange={(e) => onInputChange(e, 'materia')} required className={classNames({ 'p-invalid': submitted && !evaluador.materia })} />
                    {submitted && !evaluador.materia && <small className="p-error">La materia es requerida.</small>}
                </div>

            </Dialog>

            {/* Diálogo de Confirmación para Eliminar */}
            <Dialog visible={deleteEvaluadorDialog} style={{ width: '32rem' }} breakpoints={{ '960px': '75vw', '641px': '90vw' }} header="Confirmar" modal footer={deleteEvaluadorDialogFooter} onHide={hideDeleteEvaluadorDialog}>
                <div className="confirmation-content flex align-items-center">
                    <i className="pi pi-exclamation-triangle mr-3" style={{ fontSize: '2rem' }} />
                    {evaluador && <span>¿Estás seguro de que deseas eliminar a <b>{evaluador.nombre_Evaluador}</b>?</span>}
                </div>
            </Dialog>
        </div>
    );
}