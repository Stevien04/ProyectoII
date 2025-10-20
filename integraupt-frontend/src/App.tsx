import React, { useState, useEffect } from 'react';
import { supabase } from './utils/supabase/client';
import { LoginScreen } from './components/LoginScreen';
import { Dashboard } from './components/Dashboard';
import './styles/App.css';

interface User {
  id: string;
  email: string;
  user_metadata: {
    name: string;
    avatar_url: string;
    role?: string;
    login_type?: string;
  };
}

function App() {
  const [user, setUser] = useState<User | null>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    // Verificar si hay una sesión activa
    const checkSession = async () => {
      // Check for admin session in localStorage
      const adminSession = localStorage.getItem('admin_session');
      if (adminSession) {
        try {
          const adminUser = JSON.parse(adminSession);
          setUser(adminUser);
          setLoading(false);
          return;
        } catch (error) {
          localStorage.removeItem('admin_session');
        }
      }

      // Check regular Supabase session
      const { data: { session } } = await supabase.auth.getSession();
      setUser(session?.user as User || null);
      setLoading(false);
    };

    checkSession();

    // Escuchar cambios en la autenticación
    const { data: { subscription } } = supabase.auth.onAuthStateChange(
      (event, session) => {
        if (event === 'SIGNED_OUT') {
          localStorage.removeItem('admin_session');
        }
        setUser(session?.user as User || null);
        setLoading(false);
      }
    );

    // Listen for admin login events
    const handleAdminLogin = (event: CustomEvent) => {
      const adminUser = event.detail;
      localStorage.setItem('admin_session', JSON.stringify(adminUser));
      setUser(adminUser);
    };

    window.addEventListener('admin-login' as any, handleAdminLogin);

    return () => {
      subscription.unsubscribe();
      window.removeEventListener('admin-login' as any, handleAdminLogin);
    };
  }, []);

  if (loading) {
    return (
      <div className="app-loading-container">
        <div className="app-loading-spinner"></div>
      </div>
    );
  }

  return (
    <div className="app-container">
      {user ? (
        <Dashboard user={user} />
      ) : (
        <LoginScreen />
      )}
    </div>
  );
}

export default App;