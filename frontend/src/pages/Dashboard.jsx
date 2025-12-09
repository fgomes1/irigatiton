import { useState, useEffect } from 'react';
import { propriedadeService, dispositivoService, setorService } from '../services/api';

const StatCard = ({ title, value, icon, color, trend }) => (
    <div className="card hover:shadow-xl transition-shadow">
        <div className="flex items-center justify-between">
            <div>
                <p className="text-sm font-medium text-gray-500">{title}</p>
                <p className="text-3xl font-bold text-gray-900 mt-1">{value}</p>
                {trend && (
                    <p className={`text-sm mt-2 ${trend > 0 ? 'text-green-600' : 'text-red-600'}`}>
                        {trend > 0 ? '↑' : '↓'} {Math.abs(trend)}% em relação ao mês anterior
                    </p>
                )}
            </div>
            <div className={`w-14 h-14 rounded-xl flex items-center justify-center ${color}`}>
                {icon}
            </div>
        </div>
    </div>
);

const Dashboard = () => {
    const [stats, setStats] = useState({
        propriedades: 0,
        dispositivos: 0,
        setores: 0,
        irrigacoesHoje: 0,
    });
    const [loading, setLoading] = useState(true);
    const [recentActivities, setRecentActivities] = useState([]);

    useEffect(() => {
        loadDashboardData();
    }, []);

    const loadDashboardData = async () => {
        try {
            const [propRes, dispRes, setorRes] = await Promise.all([
                propriedadeService.listar(0, 1),
                dispositivoService.listar(0, 1),
                setorService.listar(0, 1),
            ]);

            setStats({
                propriedades: propRes.data.totalElements || 0,
                dispositivos: dispRes.data.totalElements || 0,
                setores: setorRes.data.totalElements || 0,
                irrigacoesHoje: Math.floor(Math.random() * 50) + 10,
            });

            // Mock activities
            setRecentActivities([
                { id: 1, type: 'irrigation', message: 'Irrigação iniciada no Setor A1', time: '2 min atrás', status: 'success' },
                { id: 2, type: 'device', message: 'Dispositivo ESP-32-001 conectado', time: '15 min atrás', status: 'success' },
                { id: 3, type: 'alert', message: 'Umidade baixa detectada no Setor B2', time: '1 hora atrás', status: 'warning' },
                { id: 4, type: 'irrigation', message: 'Irrigação finalizada no Setor C3', time: '2 horas atrás', status: 'success' },
                { id: 5, type: 'device', message: 'Atualização de firmware disponível', time: '3 horas atrás', status: 'info' },
            ]);
        } catch (error) {
            console.error('Erro ao carregar dados do dashboard:', error);
        } finally {
            setLoading(false);
        }
    };

    if (loading) {
        return (
            <div className="flex items-center justify-center h-64">
                <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-green-600"></div>
            </div>
        );
    }

    return (
        <div className="space-y-8">
            {/* Header */}
            <div>
                <h1 className="text-3xl font-bold text-gray-900">Dashboard</h1>
                <p className="text-gray-500 mt-1">Bem-vindo ao Sistema de Irrigação Inteligente</p>
            </div>

            {/* Stats Grid */}
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
                <StatCard
                    title="Propriedades"
                    value={stats.propriedades}
                    color="bg-blue-100"
                    icon={
                        <svg className="w-7 h-7 text-blue-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M19 21V5a2 2 0 00-2-2H7a2 2 0 00-2 2v16m14 0h2m-2 0h-5m-9 0H3m2 0h5M9 7h1m-1 4h1m4-4h1m-1 4h1m-5 10v-5a1 1 0 011-1h2a1 1 0 011 1v5m-4 0h4" />
                        </svg>
                    }
                    trend={12}
                />
                <StatCard
                    title="Dispositivos"
                    value={stats.dispositivos}
                    color="bg-green-100"
                    icon={
                        <svg className="w-7 h-7 text-green-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 3v2m6-2v2M9 19v2m6-2v2M5 9H3m2 6H3m18-6h-2m2 6h-2M7 19h10a2 2 0 002-2V7a2 2 0 00-2-2H7a2 2 0 00-2 2v10a2 2 0 002 2zM9 9h6v6H9V9z" />
                        </svg>
                    }
                    trend={8}
                />
                <StatCard
                    title="Setores"
                    value={stats.setores}
                    color="bg-purple-100"
                    icon={
                        <svg className="w-7 h-7 text-purple-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M4 5a1 1 0 011-1h14a1 1 0 011 1v2a1 1 0 01-1 1H5a1 1 0 01-1-1V5zM4 13a1 1 0 011-1h6a1 1 0 011 1v6a1 1 0 01-1 1H5a1 1 0 01-1-1v-6zM16 13a1 1 0 011-1h2a1 1 0 011 1v6a1 1 0 01-1 1h-2a1 1 0 01-1-1v-6z" />
                        </svg>
                    }
                    trend={-3}
                />
                <StatCard
                    title="Irrigações Hoje"
                    value={stats.irrigacoesHoje}
                    color="bg-amber-100"
                    icon={
                        <svg className="w-7 h-7 text-amber-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M5 3v4M3 5h4M6 17v4m-2-2h4m5-16l2.286 6.857L21 12l-5.714 2.143L13 21l-2.286-6.857L5 12l5.714-2.143L13 3z" />
                        </svg>
                    }
                    trend={24}
                />
            </div>

            {/* Main Content Grid */}
            <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
                {/* Recent Activities */}
                <div className="lg:col-span-2 card">
                    <h2 className="text-xl font-bold text-gray-900 mb-4">Atividades Recentes</h2>
                    <div className="space-y-4">
                        {recentActivities.map((activity) => (
                            <div key={activity.id} className="flex items-center gap-4 p-3 bg-gray-50 rounded-lg">
                                <div className={`w-10 h-10 rounded-full flex items-center justify-center ${activity.status === 'success' ? 'bg-green-100' :
                                        activity.status === 'warning' ? 'bg-amber-100' : 'bg-blue-100'
                                    }`}>
                                    {activity.type === 'irrigation' && (
                                        <svg className="w-5 h-5 text-green-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M5 3v4M3 5h4M6 17v4m-2-2h4m5-16l2.286 6.857L21 12l-5.714 2.143L13 21l-2.286-6.857L5 12l5.714-2.143L13 3z" />
                                        </svg>
                                    )}
                                    {activity.type === 'device' && (
                                        <svg className="w-5 h-5 text-blue-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 3v2m6-2v2M9 19v2m6-2v2M5 9H3m2 6H3m18-6h-2m2 6h-2M7 19h10a2 2 0 002-2V7a2 2 0 00-2-2H7a2 2 0 00-2 2v10a2 2 0 002 2zM9 9h6v6H9V9z" />
                                        </svg>
                                    )}
                                    {activity.type === 'alert' && (
                                        <svg className="w-5 h-5 text-amber-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 9v2m0 4h.01m-6.938 4h13.856c1.54 0 2.502-1.667 1.732-3L13.732 4c-.77-1.333-2.694-1.333-3.464 0L3.34 16c-.77 1.333.192 3 1.732 3z" />
                                        </svg>
                                    )}
                                </div>
                                <div className="flex-1">
                                    <p className="text-sm font-medium text-gray-900">{activity.message}</p>
                                    <p className="text-xs text-gray-500">{activity.time}</p>
                                </div>
                            </div>
                        ))}
                    </div>
                </div>

                {/* Quick Actions */}
                <div className="card">
                    <h2 className="text-xl font-bold text-gray-900 mb-4">Ações Rápidas</h2>
                    <div className="space-y-3">
                        <button className="w-full btn btn-primary flex items-center justify-center gap-2">
                            <svg className="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 6v6m0 0v6m0-6h6m-6 0H6" />
                            </svg>
                            Nova Propriedade
                        </button>
                        <button className="w-full btn btn-outline flex items-center justify-center gap-2">
                            <svg className="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 6v6m0 0v6m0-6h6m-6 0H6" />
                            </svg>
                            Novo Dispositivo
                        </button>
                        <button className="w-full btn btn-secondary flex items-center justify-center gap-2">
                            <svg className="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M4 4v5h.582m15.356 2A8.001 8.001 0 004.582 9m0 0H9m11 11v-5h-.581m0 0a8.003 8.003 0 01-15.357-2m15.357 2H15" />
                            </svg>
                            Iniciar Irrigação
                        </button>
                    </div>

                    {/* System Status */}
                    <div className="mt-6 pt-6 border-t border-gray-200">
                        <h3 className="font-medium text-gray-900 mb-3">Status do Sistema</h3>
                        <div className="space-y-2">
                            <div className="flex items-center justify-between">
                                <span className="text-sm text-gray-600">API Backend</span>
                                <span className="flex items-center gap-1 text-green-600 text-sm">
                                    <span className="w-2 h-2 bg-green-500 rounded-full"></span>
                                    Online
                                </span>
                            </div>
                            <div className="flex items-center justify-between">
                                <span className="text-sm text-gray-600">Banco de Dados</span>
                                <span className="flex items-center gap-1 text-green-600 text-sm">
                                    <span className="w-2 h-2 bg-green-500 rounded-full"></span>
                                    Online
                                </span>
                            </div>
                            <div className="flex items-center justify-between">
                                <span className="text-sm text-gray-600">Message Queue</span>
                                <span className="flex items-center gap-1 text-green-600 text-sm">
                                    <span className="w-2 h-2 bg-green-500 rounded-full"></span>
                                    Online
                                </span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default Dashboard;
