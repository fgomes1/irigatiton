import { useState, useEffect } from 'react';
import { dispositivoService, propriedadeService } from '../services/api';

const Dispositivos = () => {
    const [dispositivos, setDispositivos] = useState([]);
    const [propriedades, setPropriedades] = useState([]);
    const [loading, setLoading] = useState(true);
    const [showModal, setShowModal] = useState(false);
    const [editingItem, setEditingItem] = useState(null);
    const [formData, setFormData] = useState({
        nome: '',
        modelo: '',
        status: 'ativo',
        dataInstalacao: '',
        propriedadeId: '',
    });

    useEffect(() => {
        loadData();
    }, []);

    const loadData = async () => {
        try {
            const [dispRes, propRes] = await Promise.all([
                dispositivoService.listar(0, 50),
                propriedadeService.listar(0, 50),
            ]);
            setDispositivos(dispRes.data.content || []);
            setPropriedades(propRes.data.content || []);
        } catch (error) {
            console.error('Erro ao carregar dispositivos:', error);
        } finally {
            setLoading(false);
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            if (editingItem) {
                await dispositivoService.atualizar(editingItem.id, formData);
            } else {
                await dispositivoService.criar(formData);
            }
            setShowModal(false);
            setEditingItem(null);
            setFormData({ nome: '', modelo: '', status: 'ativo', dataInstalacao: '', propriedadeId: '' });
            loadData();
        } catch (error) {
            console.error('Erro ao salvar:', error);
        }
    };

    const handleEdit = (item) => {
        setEditingItem(item);
        setFormData({
            nome: item.nome,
            modelo: item.modelo || '',
            status: item.status || 'ativo',
            dataInstalacao: item.dataInstalacao || '',
            propriedadeId: item.propriedadeId || '',
        });
        setShowModal(true);
    };

    const handleDelete = async (id) => {
        if (confirm('Deseja realmente excluir este dispositivo?')) {
            try {
                await dispositivoService.deletar(id);
                loadData();
            } catch (error) {
                console.error('Erro ao excluir:', error);
            }
        }
    };

    const openNewModal = () => {
        setEditingItem(null);
        setFormData({ nome: '', modelo: '', status: 'ativo', dataInstalacao: '', propriedadeId: '' });
        setShowModal(true);
    };

    const getStatusBadge = (status) => {
        const styles = {
            ativo: 'bg-green-100 text-green-700',
            inativo: 'bg-gray-100 text-gray-700',
            manutencao: 'bg-amber-100 text-amber-700',
        };
        return (
            <span className={`px-3 py-1 rounded-full text-xs font-medium ${styles[status] || styles.inativo}`}>
                {status || 'Inativo'}
            </span>
        );
    };

    if (loading) {
        return (
            <div className="flex items-center justify-center h-64">
                <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-green-600"></div>
            </div>
        );
    }

    return (
        <div className="space-y-6">
            {/* Header */}
            <div className="flex items-center justify-between">
                <div>
                    <h1 className="text-3xl font-bold text-gray-900">Dispositivos</h1>
                    <p className="text-gray-500 mt-1">Gerencie os dispositivos IoT do sistema</p>
                </div>
                <button onClick={openNewModal} className="btn btn-primary flex items-center gap-2">
                    <svg className="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 6v6m0 0v6m0-6h6m-6 0H6" />
                    </svg>
                    Novo Dispositivo
                </button>
            </div>

            {/* Grid Cards */}
            {dispositivos.length === 0 ? (
                <div className="card text-center py-12">
                    <svg className="w-16 h-16 mx-auto text-gray-400 mb-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 3v2m6-2v2M9 19v2m6-2v2M5 9H3m2 6H3m18-6h-2m2 6h-2M7 19h10a2 2 0 002-2V7a2 2 0 00-2-2H7a2 2 0 00-2 2v10a2 2 0 002 2zM9 9h6v6H9V9z" />
                    </svg>
                    <p className="text-gray-500">Nenhum dispositivo cadastrado</p>
                </div>
            ) : (
                <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                    {dispositivos.map((item) => (
                        <div key={item.id} className="card hover:shadow-xl transition-shadow">
                            <div className="flex items-start justify-between mb-4">
                                <div className="w-12 h-12 bg-green-100 rounded-xl flex items-center justify-center">
                                    <svg className="w-6 h-6 text-green-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                        <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 3v2m6-2v2M9 19v2m6-2v2M5 9H3m2 6H3m18-6h-2m2 6h-2M7 19h10a2 2 0 002-2V7a2 2 0 00-2-2H7a2 2 0 00-2 2v10a2 2 0 002 2zM9 9h6v6H9V9z" />
                                    </svg>
                                </div>
                                {getStatusBadge(item.status)}
                            </div>
                            <h3 className="text-lg font-bold text-gray-900 mb-1">{item.nome}</h3>
                            <p className="text-sm text-gray-500 mb-4">{item.modelo || 'Modelo não informado'}</p>

                            <div className="pt-4 border-t border-gray-100 flex items-center justify-between">
                                <span className="text-xs text-gray-500">
                                    {item.dataInstalacao ? `Instalado em ${item.dataInstalacao}` : 'Data não informada'}
                                </span>
                                <div className="flex items-center gap-1">
                                    <button
                                        onClick={() => handleEdit(item)}
                                        className="p-2 text-gray-600 hover:text-blue-600 hover:bg-blue-50 rounded-lg transition-colors"
                                    >
                                        <svg className="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M11 5H6a2 2 0 00-2 2v11a2 2 0 002 2h11a2 2 0 002-2v-5m-1.414-9.414a2 2 0 112.828 2.828L11.828 15H9v-2.828l8.586-8.586z" />
                                        </svg>
                                    </button>
                                    <button
                                        onClick={() => handleDelete(item.id)}
                                        className="p-2 text-gray-600 hover:text-red-600 hover:bg-red-50 rounded-lg transition-colors"
                                    >
                                        <svg className="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16" />
                                        </svg>
                                    </button>
                                </div>
                            </div>
                        </div>
                    ))}
                </div>
            )}

            {/* Modal */}
            {showModal && (
                <div className="fixed inset-0 bg-black/50 flex items-center justify-center z-50">
                    <div className="bg-white rounded-2xl shadow-2xl w-full max-w-md p-6 m-4">
                        <div className="flex items-center justify-between mb-6">
                            <h2 className="text-xl font-bold text-gray-900">
                                {editingItem ? 'Editar Dispositivo' : 'Novo Dispositivo'}
                            </h2>
                            <button
                                onClick={() => setShowModal(false)}
                                className="p-2 hover:bg-gray-100 rounded-lg transition-colors"
                            >
                                <svg className="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M6 18L18 6M6 6l12 12" />
                                </svg>
                            </button>
                        </div>
                        <form onSubmit={handleSubmit} className="space-y-4">
                            <div>
                                <label className="label">Nome *</label>
                                <input
                                    type="text"
                                    value={formData.nome}
                                    onChange={(e) => setFormData({ ...formData, nome: e.target.value })}
                                    className="input"
                                    required
                                />
                            </div>
                            <div>
                                <label className="label">Modelo</label>
                                <input
                                    type="text"
                                    value={formData.modelo}
                                    onChange={(e) => setFormData({ ...formData, modelo: e.target.value })}
                                    className="input"
                                    placeholder="Ex: ESP32, Arduino, etc"
                                />
                            </div>
                            <div>
                                <label className="label">Propriedade *</label>
                                <select
                                    value={formData.propriedadeId}
                                    onChange={(e) => setFormData({ ...formData, propriedadeId: e.target.value })}
                                    className="input"
                                    required
                                >
                                    <option value="">Selecione uma propriedade</option>
                                    {propriedades.map((prop) => (
                                        <option key={prop.id} value={prop.id}>{prop.nome}</option>
                                    ))}
                                </select>
                            </div>
                            <div>
                                <label className="label">Status</label>
                                <select
                                    value={formData.status}
                                    onChange={(e) => setFormData({ ...formData, status: e.target.value })}
                                    className="input"
                                >
                                    <option value="ativo">Ativo</option>
                                    <option value="inativo">Inativo</option>
                                    <option value="manutencao">Em Manutenção</option>
                                </select>
                            </div>
                            <div>
                                <label className="label">Data de Instalação *</label>
                                <input
                                    type="date"
                                    value={formData.dataInstalacao}
                                    onChange={(e) => setFormData({ ...formData, dataInstalacao: e.target.value })}
                                    className="input"
                                    required
                                />
                            </div>
                            <div className="flex gap-3 pt-4">
                                <button type="button" onClick={() => setShowModal(false)} className="flex-1 btn btn-secondary">
                                    Cancelar
                                </button>
                                <button type="submit" className="flex-1 btn btn-primary">
                                    {editingItem ? 'Salvar' : 'Criar'}
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            )}
        </div>
    );
};

export default Dispositivos;
