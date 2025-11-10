import { login } from "../../../utils/auth";

const form = document.getElementById("loginForm") as HTMLFormElement;

form?.addEventListener("submit", async (event) => {
  event.preventDefault();

  const email = (document.getElementById("email") as HTMLInputElement).value.trim();
  const password = (document.getElementById("password") as HTMLInputElement).value.trim();

  if (!email || !password) {
    alert("⚠️ Por favor completá todos los campos.");
    return;
  }

  try {
    // 🔹 Intentar iniciar sesión con el backend (usa tu auth.ts)
    const user = await login(email, password);

    // 🔹 Guardar sesión actual (ya lo hace login(), pero por si acaso)
    localStorage.setItem("user", JSON.stringify(user));

    alert("✅ Inicio de sesión exitoso");

    // 🔹 Redirigir según el rol
    if (user.role === "admin") {
      window.location.href = "/src/pages/admin/adminHome/adminHome.html";
    } else {
      window.location.href = "/src/pages/store/home/home.html";
    }
  } catch (error) {
    console.error("❌ Error en login:", error);
    alert("Credenciales incorrectas o usuario no encontrado.");
  }
});
