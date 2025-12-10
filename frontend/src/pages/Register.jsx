import { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { usuarioService } from '../services/api';

const Register = () => {
    const [formData, setFormData] = useState({
        nome: '',
        email: '',
        senha: '',
        confirmarSenha: ''
    });
    const [error, setError] = useState('');
    const [loading, setLoading] = useState(false);
    const navigate = useNavigate();

    const handleChange = (e) => {
        setFormData({
            ...formData,
            [e.target.name]: e.target.value
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError('');

        if (formData.senha !== formData.confirmarSenha) {
            setError('As senhas não coincidem');
            return;
        }

        setLoading(true);

        try {
            await usuarioService.registrar({
                nome: formData.nome,
                email: formData.email,
                senha: formData.senha
            });
            navigate('/login', { state: { message: 'Cadastro realizado com sucesso! Faça login.' } });
        } catch (err) {
            setError(err.response?.data?.message || 'Erro ao realizar cadastro. Tente novamente.');
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="min-h-screen flex items-center justify-center bg-gradient-to-br from-green-400 via-green-500 to-green-600 p-4">
            {/* Background Pattern */}
            <div className="absolute inset-0 opacity-10">
                <div className="absolute inset-0" style={{
                    backgroundImage: `url("data:image/svg+xml,%3Csvg width='60' height='60' viewBox='0 0 60 60' xmlns='http://www.w3.org/2000/svg'%3E%3Cg fill='none' fill-rule='evenodd'%3E%3Cg fill='%23ffffff' fill-opacity='0.4'%3E%3Cpath d='M36 34v-4h-2v4h-4v2h4v4h2v-4h4v-2h-4zm0-30V0h-2v4h-4v2h4v4h2V6h4V4h-4zM6 34v-4H4v4H0v2h4v4h2v-4h4v-2H6zM6 4V0H4v4H0v2h4v4h2V6h4V4H6z'/%3E%3C/g%3E%3C/g%3E%3C/svg%3E")`,
                }} />
            </div>

            <div className="relative w-full max-w-md">
                {/* Logo Card */}
                <div className="text-center mb-8">
                    <div className="inline-flex items-center justify-center w-16 h-16 bg-white rounded-2xl shadow-xl mb-4">
                        <svg className="w-10 h-10 text-green-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M18 9v3m0 0v3m0-3h3m-3 0h-3m-2-5a4 4 0 11-8 0 4 4 0 018 0zM3 20a6 6 0 0112 0v1H3v-1z" />
                        </svg>
                    </div>
                    <h1 className="text-2xl font-bold text-white mb-2">Criar Conta</h1>
                    <p className="text-green-100">Junte-se ao Sistema de Irrigação Inteligente</p>
                </div>

                {/* Register Form */}
                <div className="bg-white rounded-2xl shadow-2xl p-8">
                    {error && (
                        <div className="mb-4 p-4 bg-red-50 border border-red-200 rounded-lg text-red-600 text-sm">
                            {error}
                        </div>
                    )}

                    <form onSubmit={handleSubmit} className="space-y-5">
                        <div>
                            <label htmlFor="nome" className="block text-sm font-medium text-gray-700 mb-1">
                                Nome Completo
                            </label>
                            <input
                                id="nome"
                                name="nome"
                                type="text"
                                value={formData.nome}
                                onChange={handleChange}
                                className="w-full px-4 py-2 rounded-lg border border-gray-300 focus:outline-none focus:ring-2 focus:ring-green-500 focus:border-transparent transition-all duration-200"
                                placeholder="Seu nome"
                                required
                            />
                        </div>

                        <div>
                            <label htmlFor="email" className="block text-sm font-medium text-gray-700 mb-1">
                                E-mail
                            </label>
                            <input
                                id="email"
                                name="email"
                                type="email"
                                value={formData.email}
                                onChange={handleChange}
                                className="w-full px-4 py-2 rounded-lg border border-gray-300 focus:outline-none focus:ring-2 focus:ring-green-500 focus:border-transparent transition-all duration-200"
                                placeholder="seu@email.com"
                                required
                            />
                        </div>

                        <div>
                            <label htmlFor="senha" className="block text-sm font-medium text-gray-700 mb-1">
                                Senha
                            </label>
                            <input
                                id="senha"
                                name="senha"
                                type="password"
                                value={formData.senha}
                                onChange={handleChange}
                                className="w-full px-4 py-2 rounded-lg border border-gray-300 focus:outline-none focus:ring-2 focus:ring-green-500 focus:border-transparent transition-all duration-200"
                                placeholder="••••••••"
                                required
                                minLength={6}
                            />
                        </div>

                        <div>
                            <label htmlFor="confirmarSenha" className="block text-sm font-medium text-gray-700 mb-1">
                                Confirmar Senha
                            </label>
                            <input
                                id="confirmarSenha"
                                name="confirmarSenha"
                                type="password"
                                value={formData.confirmarSenha}
                                onChange={handleChange}
                                className="w-full px-4 py-2 rounded-lg border border-gray-300 focus:outline-none focus:ring-2 focus:ring-green-500 focus:border-transparent transition-all duration-200"
                                placeholder="••••••••"
                                required
                                minLength={6}
                            />
                        </div>

                        <button
                            type="submit"
                            disabled={loading}
                            className="w-full px-4 py-3 bg-green-600 text-white rounded-lg font-medium text-lg hover:bg-green-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-green-500 transition-all duration-200 disabled:opacity-50 disabled:cursor-not-allowed mt-2"
                        >
                            {loading ? (
                                <span className="flex items-center justify-center gap-2">
                                    <svg className="animate-spin w-5 h-5" fill="none" viewBox="0 0 24 24">
                                        <circle className="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" strokeWidth="4" />
                                        <path className="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z" />
                                    </svg>
                                    CRIANDO CONTA...
                                </span>
                            ) : (
                                'CRIAR CONTA'
                            )}
                        </button>

                        <div className="mt-6 text-center">
                            <p className="text-gray-600 text-sm">
                                Já tem uma conta?{' '}
                                <Link to="/login" className="text-green-600 hover:text-green-700 font-medium">
                                    Fazer Login
                                </Link>
                            </p>
                        </div>
                    </form>
                </div>

                <p className="text-center text-green-100 text-sm mt-8">
                    © 2024 Sistema de Irrigação Inteligente - UTFPR
                </p>
            </div>
        </div>
    );
};

export default Register;
