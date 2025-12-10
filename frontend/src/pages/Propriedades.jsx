import { useState, useEffect } from 'react';
import { propriedadeService } from '../services/api';

import { useAuth } from '../context/AuthContext';

const Propriedades = () => {
    const { user } = useAuth();
    const [propriedades, setPropriedades] = useState([]);
    const [loading, setLoading] = useState(true);
    const [showModal, setShowModal] = useState(false);
    const [editingItem, setEditingItem] = useState(null);
    const [formData, setFormData] = useState({
        nome: '',
        localizacao: '',
        tamanho: '',
        usuarioId: '',
    });

    useEffect(() => {
        loadData();
    }, []);

    const loadData = async () => {
        try {
            const response = await propriedadeService.listar(0, 50);
            setPropriedades(response.data.content || []);
        } catch (error) {
            console.error('Erro ao carregar propriedades:', error);
        } finally {
            setLoading(false);
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        // Ensure usuarioId is set
        const dataToSend = {
            ...formData,
            usuarioId: formData.usuarioId || user?.sub
        };

        if (!dataToSend.usuarioId) {
            alert("Erro: Usuário não identificado. Faça login novamente.");
            return;
        }

        try {
            if (editingItem) {
                await propriedadeService.atualizar(editingItem.id, dataToSend);
            } else {
                await propriedadeService.criar(dataToSend);
            }
            setShowModal(false);
            setEditingItem(null);
            setFormData({ nome: '', localizacao: '', tamanho: '', usuarioId: '' });
            loadData();
        } catch (error) {
            console.error('Erro ao salvar:', error);
            alert('Erro ao salvar propriedade. Verifique os dados.');
        }
    };

    const handleEdit = (item) => {
        setEditingItem(item);
        setFormData({
            nome: item.nome,
            localizacao: item.localizacao || '',
            tamanho: item.tamanho || '',
            usuarioId: item.usuarioId || '',
        });
        setShowModal(true);
    };

    const handleDelete = async (id) => {
        if (confirm('Deseja realmente excluir esta propriedade?')) {
            try {
                await propriedadeService.deletar(id);
                loadData();
            } catch (error) {
                console.error('Erro ao excluir:', error);
            }
        }
    };

    const openNewModal = () => {
        setEditingItem(null);
        setFormData({ nome: '', localizacao: '', tamanho: '', usuarioId: '' });
        setShowModal(true);
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
                    <h1 className="text-3xl font-bold text-gray-900">Propriedades</h1>
                    <p className="text-gray-500 mt-1">Gerencie suas propriedades rurais</p>
                </div>
                <button onClick={openNewModal} className="btn btn-primary flex items-center gap-2">
                    <svg className="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 6v6m0 0v6m0-6h6m-6 0H6" />
                    </svg>
                    Nova Propriedade
                </button>
            </div>

            {/* Table */}
            <div className="card overflow-hidden p-0">
                <table className="w-full">
                    <thead className="bg-gray-50 border-b border-gray-200">
                        <tr>
                            <th className="px-6 py-4 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Nome</th>
                            <th className="px-6 py-4 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Localização</th>
                            <th className="px-6 py-4 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Tamanho (ha)</th>
                            <th className="px-6 py-4 text-right text-xs font-medium text-gray-500 uppercase tracking-wider">Ações</th>
                        </tr>
                    </thead>
                    <tbody className="divide-y divide-gray-200">
                        {propriedades.length === 0 ? (
                            <tr>
                                <td colSpan="4" className="px-6 py-12 text-center text-gray-500">
                                    Nenhuma propriedade cadastrada
                                </td>
                            </tr>
                        ) : (
                            propriedades.map((item) => (
                                <tr key={item.id} className="hover:bg-gray-50 transition-colors">
                                    <td className="px-6 py-4">
                                        <div className="flex items-center gap-3">
                                            <div className="w-10 h-10 bg-blue-100 rounded-lg flex items-center justify-center">
                                                <svg className="w-5 h-5 text-blue-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M19 21V5a2 2 0 00-2-2H7a2 2 0 00-2 2v16m14 0h2m-2 0h-5m-9 0H3m2 0h5M9 7h1m-1 4h1m4-4h1m-1 4h1m-5 10v-5a1 1 0 011-1h2a1 1 0 011 1v5m-4 0h4" />
                                                </svg>
                                            </div>
                                            <span className="font-medium text-gray-900">{item.nome}</span>
                                        </div>
                                    </td>
                                    <td className="px-6 py-4 text-gray-600">{item.localizacao || '-'}</td>
                                    <td className="px-6 py-4 text-gray-600">{item.tamanho || '-'}</td>
                                    <td className="px-6 py-4 text-right">
                                        <div className="flex items-center justify-end gap-2">
                                            <button
                                                onClick={() => handleEdit(item)}
                                                className="p-2 text-gray-600 hover:text-blue-600 hover:bg-blue-50 rounded-lg transition-colors"
                                            >
                                                <svg className="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M11 5H6a2 2 0 00-2 2v11a2 2 0 002 2h11a2 2 0 002-2v-5m-1.414-9.414a2 2 0 112.828 2.828L11.828 15H9v-2.828l8.586-8.586z" />
                                                </svg>
                                            </button>
                                            <button
                                                onClick={() => handleDelete(item.id)}
                                                className="p-2 text-gray-600 hover:text-red-600 hover:bg-red-50 rounded-lg transition-colors"
                                            >
                                                <svg className="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16" />
                                                </svg>
                                            </button>
                                        </div>
                                    </td>
                                </tr>
                            ))
                        )}
                    </tbody>
                </table>
            </div>

            {/* Modal */}
            {showModal && (
                <div className="fixed inset-0 bg-black/50 flex items-center justify-center z-50">
                    <div className="bg-white rounded-2xl shadow-2xl w-full max-w-md p-6 m-4">
                        <div className="flex items-center justify-between mb-6">
                            <h2 className="text-xl font-bold text-gray-900">
                                {editingItem ? 'Editar Propriedade' : 'Nova Propriedade'}
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
                                <label className="label">Localização</label>
                                <input
                                    type="text"
                                    value={formData.localizacao}
                                    onChange={(e) => setFormData({ ...formData, localizacao: e.target.value })}
                                    className="input"
                                />
                            </div>
                            <div>
                                <label className="label">Tamanho (hectares)</label>
                                <input
                                    type="number"
                                    step="0.01"
                                    value={formData.tamanho}
                                    onChange={(e) => setFormData({ ...formData, tamanho: e.target.value })}
                                    className="input"
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

export default Propriedades;
