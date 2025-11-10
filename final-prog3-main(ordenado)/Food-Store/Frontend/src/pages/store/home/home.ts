import { getCurrentUser, logout } from "../../../utils/auth";

// ============================
// 🔹 Mostrar usuario logueado (opcional)
// ============================
const user = getCurrentUser();

if (user) {
  console.log("Usuario logueado:", user);
  // Si querés mostrar el nombre del usuario en pantalla:
  const header = document.querySelector("header h1");
  if (header && user.name) {
    header.textContent = `🍔 Food Store - Hola, ${user.name}`;
  }
} else {
  // Si no hay usuario, redirigimos al login
  window.location.href = "/src/pages/auth/login/login.html";
}

// ============================
// 🔹 Botón de Cerrar Sesión
// ============================
const logoutBtn = document.getElementById("logoutBtn");

if (logoutBtn) {
  logoutBtn.addEventListener("click", () => {
    const confirmLogout = confirm("¿Seguro que deseas cerrar sesión?");
    if (confirmLogout) {
      logout(); // elimina el user del localStorage
      window.location.href = "/src/pages/auth/login/login.html"; // redirige al login
    }
  });
}

// ============================
// 🔹 Ejemplo: Lógica de productos (simulada)
// ============================

// Si después querés traer productos desde tu backend, este es un ejemplo base:
async function fetchProducts() {
  try {
    const response = await fetch("http://localhost:8080/api/products");
    if (!response.ok) throw new Error("Error al cargar los productos");

    const products = await response.json();
    renderProducts(products);
  } catch (error) {
    console.error(error);
  }
}

function renderProducts(products: any[]) {
  const container = document.querySelector(".store-container");
  if (!container) return;

  container.innerHTML = ""; // Limpia los productos previos

  products.forEach((product) => {
    const card = document.createElement("div");
    card.classList.add("product-card");
    card.innerHTML = `
      <img src="${product.image}" alt="${product.name}" />
      <h2>${product.name}</h2>
      <p>$${product.price}</p>
      <button class="add-to-cart">Agregar al carrito</button>
    `;
    container.appendChild(card);
  });
}

// Descomenta esta línea si ya tenés el backend funcionando:
// fetchProducts();

console.log("✅ Home cargado correctamente");
