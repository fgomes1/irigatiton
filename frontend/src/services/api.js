import axios from 'axios';

const API_BASE_URL = 'http://localhost:8081';

const api = axios.create({
    baseURL: API_BASE_URL,
    headers: {
        'Content-Type': 'application/json',
    },
});

// Interceptor para adicionar token de autenticação
api.interceptors.request.use(
    (config) => {
        const token = localStorage.getItem('accessToken');
        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
    },
    (error) => Promise.reject(error)
);

// Interceptor para tratar erros de resposta
api.interceptors.response.use(
    (response) => response,
    (error) => {
        if (error.response?.status === 401) {
            localStorage.removeItem('accessToken');
            localStorage.removeItem('refreshToken');
            window.location.href = '/login';
        }
        return Promise.reject(error);
    }
);

// Auth
export const authService = {
    login: (credentials) => api.post('/auth/login', credentials),
    validate: () => api.post('/auth/validate'),
    refresh: (refreshToken, username) => api.post('/auth/refresh', { refreshToken, username }),
};

// Usuários
export const usuarioService = {
    listar: (pagina = 0, tamanho = 10) => api.get(`/api/usuarios?pagina=${pagina}&tamanho=${tamanho}`),
    buscar: (id) => api.get(`/api/usuarios/${id}`),
    registrar: (data) => api.post('/api/usuarios/registro', data),
    criar: (data) => api.post('/api/usuarios', data),
    atualizar: (id, data) => api.put(`/api/usuarios/${id}`, data),
    deletar: (id) => api.delete(`/api/usuarios/${id}`),
};

// Propriedades
export const propriedadeService = {
    listar: (pagina = 0, tamanho = 10) => api.get(`/api/propriedades?pagina=${pagina}&tamanho=${tamanho}`),
    buscar: (id) => api.get(`/api/propriedades/${id}`),
    criar: (data) => api.post('/api/propriedades', data),
    atualizar: (id, data) => api.put(`/api/propriedades/${id}`, data),
    deletar: (id) => api.delete(`/api/propriedades/${id}`),
};

// Dispositivos
export const dispositivoService = {
    listar: (pagina = 0, tamanho = 10) => api.get(`/api/dispositivos?pagina=${pagina}&tamanho=${tamanho}`),
    buscar: (id) => api.get(`/api/dispositivos/${id}`),
    criar: (data) => api.post('/api/dispositivos', data),
    atualizar: (id, data) => api.put(`/api/dispositivos/${id}`, data),
    deletar: (id) => api.delete(`/api/dispositivos/${id}`),
};

// Setores
export const setorService = {
    listar: (pagina = 0, tamanho = 10) => api.get(`/api/setores?pagina=${pagina}&tamanho=${tamanho}`),
    buscar: (id) => api.get(`/api/setores/${id}`),
    criar: (data) => api.post('/api/setores', data),
    atualizar: (id, data) => api.put(`/api/setores/${id}`, data),
    deletar: (id) => api.delete(`/api/setores/${id}`),
};

export default api;
