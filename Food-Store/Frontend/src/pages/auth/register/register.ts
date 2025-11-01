import { register } from "../../../utils/auth";

const registerForm = document.getElementById("registerForm") as HTMLFormElement;
const nameInput = document.getElementById("name") as HTMLInputElement;
const emailInput = document.getElementById("email") as HTMLInputElement;
const passwordInput = document.getElementById("password") as HTMLInputElement;
const passwordHelp = document.getElementById("passwordHelp") as HTMLElement;

registerForm.addEventListener("submit", async (e) => {
  e.preventDefault();

  const name = nameInput.value.trim();
  const email = emailInput.value.trim();
  const password = passwordInput.value.trim();

  // 🔹 Validar campos vacíos
  if (!name || !email || !password) {
    alert("Por favor, completá todos los campos.");
    return;
  }

  // 🔹 Validar contraseña segura
  const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z]).{6,}$/;
  if (!passwordRegex.test(password)) {
    passwordHelp.style.color = "red";
    alert("La contraseña debe tener al menos 6 caracteres, una mayúscula y una minúscula.");
    return;
  }

  try {
    // 🔹 Registrar usuario (usa el endpoint del backend)
    const user = await register(name, email, password);

    // ✅ Guardar sesión actual (ya lo hace register(), pero lo reafirmamos)
    localStorage.setItem("user", JSON.stringify(user));

    alert("🎉 Registro exitoso. Serás redirigido al inicio.");

    // 🔹 Redirigir según el rol
    setTimeout(() => {
      if (user.role === "admin") {
        window.location.href = "/src/pages/admin/adminHome/adminHome.html";
      } else {
        window.location.href = "/src/pages/store/home/home.html";
      }
    }, 1200);

  } catch (error) {
    console.error("❌ Error al registrar:", error);
    alert("Hubo un error al registrarte. Verificá los datos o intentá más tarde.");
  }
});
