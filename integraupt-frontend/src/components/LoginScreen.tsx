import React, { useState, useEffect } from 'react';
import {
  GraduationCap,
  ChevronRight,
  ArrowLeft,
  User,
  Lock,
  RefreshCw,
  KeyRound
} from 'lucide-react';
import './../styles/LoginScreen.css';

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080';

interface PerfilResponse {
  id?: string;
  codigo?: string;
  nombres?: string;
  apellidos?: string;
  email?: string;
  rol?: string;
  tipoLogin?: string;
  avatarUrl?: string;
  estado?: string;
}

interface LoginResponse {
  success: boolean;
  message?: string;
  perfil?: PerfilResponse;
  token?: string;
}

const mapLoginType = (tipo?: string) => {
  if (!tipo) return 'academic';
  const normalized = tipo.toLowerCase();
  if (normalized === 'administrativo' || normalized === 'administrativa') {
    return 'administrative';
  }
  return 'academic';
};

const buildUserFromPerfil = (perfil: PerfilResponse, token?: string) => {
  const fullName = [perfil.nombres, perfil.apellidos].filter(Boolean).join(' ').trim();
  const loginType = mapLoginType(perfil.tipoLogin);

  return {
    id: perfil.id || perfil.codigo || 'integraupt-user',
    email: perfil.email || (perfil.codigo ? `${perfil.codigo}@upt.edu.pe` : 'usuario@upt.edu.pe'),
    token,
    perfil,
    user_metadata: {
      name: fullName || perfil.email || 'Usuario IntegraUPT',
      avatar_url: perfil.avatarUrl || '',
      role: loginType === 'administrative' ? 'admin' : 'student',
      login_type: loginType,
      codigo: perfil.codigo,
    },
  };
};

export const LoginScreen: React.FC = () => {
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const [loginMode, setLoginMode] = useState<'selection' | 'academic' | 'administrative'>('selection');
  const [captcha, setCaptcha] = useState('');
  const [captchaInput, setCaptchaInput] = useState('');
  const [academicCredentials, setAcademicCredentials] = useState({ codigo: '', password: '' });
  const [adminCredentials, setAdminCredentials] = useState({ username: '', password: '' });

  // Generar captcha de 4 dígitos únicos
  const generateCaptcha = () => {
    const digits = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9'];
    const shuffled = digits.sort(() => Math.random() - 0.5);
    return shuffled.slice(0, 4).join('');
  };

  useEffect(() => {
    if (loginMode === 'academic') {
      setCaptcha(generateCaptcha());
    }
  }, [loginMode]);

  const refreshCaptcha = () => {
    setCaptcha(generateCaptcha());
    setCaptchaInput('');
  };

  const handleAcademicLogin = async (e: React.FormEvent) => {
    e.preventDefault();

    if (captchaInput !== captcha) {
      setError('El código CAPTCHA es incorrecto');
      refreshCaptcha();
      return;
    }

    try {
      setLoading(true);
      setError(null);

      const response = await fetch(`${API_BASE_URL}/api/auth/login`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          codigoOEmail: academicCredentials.codigo.trim(),
          password: academicCredentials.password,
          tipoLogin: 'academico'
        })
      });

      let data: LoginResponse | null = null;

      try {
        data = await response.json();
      } catch (jsonError) {
        console.error('No se pudo leer la respuesta del backend:', jsonError);
      }

      if (!response.ok || !data) {
        setError(data?.message || 'No se pudo iniciar sesión. Verifica tus datos e inténtalo nuevamente.');
        refreshCaptcha();
        return;
      }

      if (!data.success || !data.perfil) {
        setError(data.message || 'Credenciales inválidas.');
        refreshCaptcha();
        return;
      }

      const academicUser = buildUserFromPerfil(data.perfil, data.token);
      window.dispatchEvent(new CustomEvent('admin-login', { detail: academicUser }));

      setAcademicCredentials({ codigo: '', password: '' });
      setCaptchaInput('');
      setCaptcha(generateCaptcha());
    } catch (err) {
      setError('Error inesperado al intentar iniciar sesión');
      console.error('Academic login error:', err);
      refreshCaptcha();
    } finally {
      setLoading(false);
    }
  };

  const handleAdminLogin = async (e: React.FormEvent) => {
    e.preventDefault();

    try {
      setLoading(true);
      setError(null);

      const response = await fetch(`${API_BASE_URL}/api/auth/login`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          codigoOEmail: adminCredentials.username.trim(),
          password: adminCredentials.password,
          tipoLogin: 'administrativo'
        })
      });

      let data: LoginResponse | null = null;

      try {
        data = await response.json();
      } catch (jsonError) {
        console.error('No se pudo leer la respuesta del backend:', jsonError);
      }

      if (!response.ok || !data) {
        setError(data?.message || 'No se pudo iniciar sesión. Verifica tus datos e inténtalo nuevamente.');
        return;
      }

      if (!data.success || !data.perfil) {
        setError(data.message || 'Credenciales inválidas.');
        return;
      }

      const adminUser = buildUserFromPerfil(data.perfil, data.token);
      window.dispatchEvent(new CustomEvent('admin-login', { detail: adminUser }));

      setAdminCredentials({ username: '', password: '' });
    } catch (err) {
      setError('Error inesperado al intentar iniciar sesión');
      console.error('Admin login error:', err);
    } finally {
      setLoading(false);
    }
  };

  const resetToSelection = () => {
    setLoginMode('selection');
    setError(null);
    setAcademicCredentials({ codigo: '', password: '' });
    setAdminCredentials({ username: '', password: '' });
    setCaptchaInput('');
  };

  // Pantalla de selección
  if (loginMode === 'selection') {
    return (
      <div className="login-container">
        <div className="login-wrapper">
          <div className="login-header">
            <div className="login-logo login-logo-blue">
              <GraduationCap className="login-logo-icon" />
            </div>
            <h1 className="login-title">IntegraUPT</h1>
            <p className="login-subtitle">Selecciona el tipo de acceso</p>
          </div>

          <div className="login-card">
            <button onClick={() => setLoginMode('academic')} className="login-btn login-btn-blue">
              <User className="login-btn-icon" /> Académico
              <ChevronRight className="login-btn-arrow" />
            </button>

            <button onClick={() => setLoginMode('administrative')} className="login-btn login-btn-green">
              <KeyRound className="login-btn-icon" /> Administrativo
              <ChevronRight className="login-btn-arrow" />
            </button>
          </div>
        </div>
      </div>
    );
  }

  // Pantalla de login académico
  if (loginMode === 'academic') {
    return (
      <div className="login-container">
        <div className="login-wrapper">
          <div className="login-header">
            <button onClick={resetToSelection} className="back-button">
              <ArrowLeft />
            </button>
            <h1 className="login-title">Acceso Académico</h1>
          </div>

          <form className="login-form" onSubmit={handleAcademicLogin}>
            <div className="input-group">
              <User className="input-icon" />
              <input
                type="text"
                placeholder="Código o correo"
                value={academicCredentials.codigo}
                onChange={(e) =>
                  setAcademicCredentials({ ...academicCredentials, codigo: e.target.value })
                }
                required
              />
            </div>

            <div className="input-group">
              <Lock className="input-icon" />
              <input
                type="password"
                placeholder="Contraseña"
                value={academicCredentials.password}
                onChange={(e) =>
                  setAcademicCredentials({ ...academicCredentials, password: e.target.value })
                }
                required
              />
            </div>

            <div className="captcha-group">
              <span className="captcha-code">{captcha}</span>
              <button type="button" onClick={refreshCaptcha} className="captcha-refresh">
                <RefreshCw size={18} />
              </button>
            </div>

            <input
              type="text"
              placeholder="Ingrese el código CAPTCHA"
              value={captchaInput}
              onChange={(e) => setCaptchaInput(e.target.value)}
              required
            />

            {error && <p className="error-message">{error}</p>}

            <button type="submit" className="login-submit" disabled={loading}>
              {loading ? 'Ingresando...' : 'Iniciar Sesión'}
            </button>
          </form>
        </div>
      </div>
    );
  }

  // Pantalla de login administrativo
  if (loginMode === 'administrative') {
    return (
      <div className="login-container">
        <div className="login-wrapper">
          <div className="login-header">
            <button onClick={resetToSelection} className="back-button">
              <ArrowLeft />
            </button>
            <h1 className="login-title">Acceso Administrativo</h1>
          </div>

          <form className="login-form" onSubmit={handleAdminLogin}>
            <div className="input-group">
              <User className="input-icon" />
              <input
                type="text"
                placeholder="Usuario o correo"
                value={adminCredentials.username}
                onChange={(e) =>
                  setAdminCredentials({ ...adminCredentials, username: e.target.value })
                }
                required
              />
            </div>

            <div className="input-group">
              <Lock className="input-icon" />
              <input
                type="password"
                placeholder="Contraseña"
                value={adminCredentials.password}
                onChange={(e) =>
                  setAdminCredentials({ ...adminCredentials, password: e.target.value })
                }
                required
              />
            </div>

            {error && <p className="error-message">{error}</p>}

            <button type="submit" className="login-submit" disabled={loading}>
              {loading ? 'Ingresando...' : 'Iniciar Sesión'}
            </button>
          </form>
        </div>
      </div>
    );
  }

  return null;
};
