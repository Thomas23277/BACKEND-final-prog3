// =========================
// 🔐 auth.ts — Manejo de autenticación local y remota
// =========================

// --- LOGIN ---
export async function login(email: string, password: string) {
  try {
    const response = await fetch("http://localhost:8080/api/auth/login", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ email, password }),
    });

    if (!response.ok) {
      throw new Error("Credenciales incorrectas");
    }

    const userData = await response.json();

    // ✅ Guarda el usuario actual en localStorage
    localStorage.setItem("user", JSON.stringify(userData));

    return userData;
  } catch (error) {
    console.error("❌ Error al iniciar sesión:", error);
    throw error;
  }
}

// --- REGISTER ---
export async function register(name: string, email: string, password: string) {
  try {
    const response = await fetch("http://localhost:8080/api/auth/register", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ name, email, password }),
    });

    if (!response.ok) {
      throw new Error("Error al registrarse");
    }

    const userData = await response.json();

    // ✅ Guarda el nuevo usuario también en localStorage
    localStorage.setItem("user", JSON.stringify(userData));

    return userData;
  } catch (error) {
    console.error("❌ Error en el registro:", error);
    throw error;
  }
}

// --- LOGOUT ---
export function logout() {
  console.log("👋 Cerrando sesión y limpiando localStorage...");
  localStorage.removeItem("user");
}

// --- OBTENER USUARIO ACTUAL ---
export function getCurrentUser() {
  try {
    const data = localStorage.getItem("user");
    if (!data) return null;
    return JSON.parse(data);
  } catch (error) {
    console.error("⚠️ Error al leer usuario de localStorage:", error);
    return null;
  }
}
