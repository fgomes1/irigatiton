import { useState, useEffect } from 'react';
import { setorService, dispositivoService } from '../services/api';

const Setores = () => {
    const [setores, setSetores] = useState([]);
    const [dispositivos, setDispositivos] = useState([]);
    const [loading, setLoading] = useState(true);
    const [showModal, setShowModal] = useState(false);
    const [editingItem, setEditingItem] = useState(null);
    const [formData, setFormData] = useState({
        nome: '',
        area: '',
        tipoCultura: '',
        status: 'ativo',
        horariosIrrigacao: '',
        dispositivoId: '',
    });

    useEffect(() => {
        loadData();
    }, []);

    const loadData = async () => {
        try {
            const [setorRes, dispRes] = await Promise.all([
                setorService.listar(0, 50),
                dispositivoService.listar(0, 50),
            ]);
            setSetores(setorRes.data.content || []);
            setDispositivos(dispRes.data.content || []);
        } catch (error) {
            console.error('Erro ao carregar setores:', error);
        } finally {
            setLoading(false);
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            if (editingItem) {
                await setorService.atualizar(editingItem.id, formData);
            } else {
                await setorService.criar(formData);
            }
            setShowModal(false);
            setEditingItem(null);
            setFormData({ nome: '', area: '', tipoCultura: '', status: 'ativo', horariosIrrigacao: '', dispositivoId: '' });
            loadData();
        } catch (error) {
            console.error('Erro ao salvar:', error);
        }
    };

    const handleEdit = (item) => {
        setEditingItem(item);
        setFormData({
            nome: item.nome,
            area: item.area || '',
            tipoCultura: item.tipoCultura || '',
            status: item.status || 'ativo',
            horariosIrrigacao: item.horariosIrrigacao || '',
            dispositivoId: item.dispositivoId || '',
        });
        setShowModal(true);
    };

    const handleDelete = async (id) => {
        if (confirm('Deseja realmente excluir este setor?')) {
            try {
                await setorService.deletar(id);
                loadData();
            } catch (error) {
                console.error('Erro ao excluir:', error);
            }
        }
    };

    const openNewModal = () => {
        setEditingItem(null);
        setFormData({ nome: '', area: '', tipoCultura: '', status: 'ativo', horariosIrrigacao: '', dispositivoId: '' });
        setShowModal(true);
    };

    const getStatusBadge = (status) => {
        const styles = {
            ativo: 'bg-green-100 text-green-700',
            inativo: 'bg-gray-100 text-gray-700',
            irrigando: 'bg-blue-100 text-blue-700',
        };
        return (
            <span className={`px-3 py-1 rounded-full text-xs font-medium ${styles[status] || styles.inativo}`}>
                {status === 'irrigando' ? '💧 Irrigando' : status || 'Inativo'}
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
                    <h1 className="text-3xl font-bold text-gray-900">Setores</h1>
                    <p className="text-gray-500 mt-1">Gerencie os setores de irrigação</p>
                </div>
                <button onClick={openNewModal} className="btn btn-primary flex items-center gap-2">
                    <svg className="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 6v6m0 0v6m0-6h6m-6 0H6" />
                    </svg>
                    Novo Setor
                </button>
            </div>

            {/* Grid Cards */}
            {setores.length === 0 ? (
                <div className="card text-center py-12">
                    <svg className="w-16 h-16 mx-auto text-gray-400 mb-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M4 5a1 1 0 011-1h14a1 1 0 011 1v2a1 1 0 01-1 1H5a1 1 0 01-1-1V5zM4 13a1 1 0 011-1h6a1 1 0 011 1v6a1 1 0 01-1 1H5a1 1 0 01-1-1v-6zM16 13a1 1 0 011-1h2a1 1 0 011 1v6a1 1 0 01-1 1h-2a1 1 0 01-1-1v-6z" />
                    </svg>
                    <p className="text-gray-500">Nenhum setor cadastrado</p>
                </div>
            ) : (
                <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                    {setores.map((item) => (
                        <div key={item.id} className="card hover:shadow-xl transition-shadow">
                            <div className="flex items-start justify-between mb-4">
                                <div className="w-12 h-12 bg-purple-100 rounded-xl flex items-center justify-center">
                                    <svg className="w-6 h-6 text-purple-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                        <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M4 5a1 1 0 011-1h14a1 1 0 011 1v2a1 1 0 01-1 1H5a1 1 0 01-1-1V5zM4 13a1 1 0 011-1h6a1 1 0 011 1v6a1 1 0 01-1 1H5a1 1 0 01-1-1v-6zM16 13a1 1 0 011-1h2a1 1 0 011 1v6a1 1 0 01-1 1h-2a1 1 0 01-1-1v-6z" />
                                    </svg>
                                </div>
                                {getStatusBadge(item.status)}
                            </div>
                            <h3 className="text-lg font-bold text-gray-900 mb-1">{item.nome}</h3>

                            <div className="space-y-2 mb-4">
                                <div className="flex items-center gap-2 text-sm text-gray-600">
                                    <svg className="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                        <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M4 8V4m0 0h4M4 4l5 5m11-1V4m0 0h-4m4 0l-5 5M4 16v4m0 0h4m-4 0l5-5m11 5l-5-5m5 5v-4m0 4h-4" />
                                    </svg>
                                    <span>{item.area ? `${item.area} hectares` : 'Área não informada'}</span>
                                </div>
                                <div className="flex items-center gap-2 text-sm text-gray-600">
                                    <svg className="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                        <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M3.055 11H5a2 2 0 012 2v1a2 2 0 002 2 2 2 0 012 2v2.945M8 3.935V5.5A2.5 2.5 0 0010.5 8h.5a2 2 0 012 2 2 2 0 104 0 2 2 0 012-2h1.064M15 20.488V18a2 2 0 012-2h3.064M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
                                    </svg>
                                    <span>{item.tipoCultura || 'Cultura não informada'}</span>
                                </div>
                                {item.ultimaIrrigacao && (
                                    <div className="flex items-center gap-2 text-sm text-gray-600">
                                        <svg className="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z" />
                                        </svg>
                                        <span>Última irrigação: {item.ultimaIrrigacao}</span>
                                    </div>
                                )}
                            </div>

                            <div className="pt-4 border-t border-gray-100 flex items-center justify-between">
                                <button className="text-sm text-green-600 hover:text-green-700 font-medium flex items-center gap-1">
                                    <svg className="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                        <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M5 3v4M3 5h4M6 17v4m-2-2h4m5-16l2.286 6.857L21 12l-5.714 2.143L13 21l-2.286-6.857L5 12l5.714-2.143L13 3z" />
                                    </svg>
                                    Iniciar Irrigação
                                </button>
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
                    <div className="bg-white rounded-2xl shadow-2xl w-full max-w-md p-6 m-4 max-h-[90vh] overflow-y-auto">
                        <div className="flex items-center justify-between mb-6">
                            <h2 className="text-xl font-bold text-gray-900">
                                {editingItem ? 'Editar Setor' : 'Novo Setor'}
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
                                <label className="label">Dispositivo *</label>
                                <select
                                    value={formData.dispositivoId}
                                    onChange={(e) => setFormData({ ...formData, dispositivoId: e.target.value })}
                                    className="input"
                                    required
                                >
                                    <option value="">Selecione um dispositivo</option>
                                    {dispositivos.map((disp) => (
                                        <option key={disp.id} value={disp.id}>{disp.nome}</option>
                                    ))}
                                </select>
                            </div>
                            <div>
                                <label className="label">Área (hectares)</label>
                                <input
                                    type="number"
                                    step="0.01"
                                    value={formData.area}
                                    onChange={(e) => setFormData({ ...formData, area: e.target.value })}
                                    className="input"
                                />
                            </div>
                            <div>
                                <label className="label">Tipo de Cultura</label>
                                <input
                                    type="text"
                                    value={formData.tipoCultura}
                                    onChange={(e) => setFormData({ ...formData, tipoCultura: e.target.value })}
                                    className="input"
                                    placeholder="Ex: Soja, Milho, Café..."
                                />
                            </div>
                            <div>
                                <label className="label">Horários de Irrigação</label>
                                <input
                                    type="text"
                                    value={formData.horariosIrrigacao}
                                    onChange={(e) => setFormData({ ...formData, horariosIrrigacao: e.target.value })}
                                    className="input"
                                    placeholder="Ex: 06:00, 18:00"
                                />
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
                                    <option value="irrigando">Irrigando</option>
                                </select>
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

export default Setores;
