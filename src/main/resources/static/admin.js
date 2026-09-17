const ADMIN_CREDENTIALS = { username: "admin", password: "admin123" };

function checkAdminAccess() {
    return localStorage.getItem("isAdminLoggedIn") === "true";
}

function verifyAdminCredentials() {
    const user = document.getElementById("adminUsername").value;
    const pass = document.getElementById("adminPassword").value;

    if (user === ADMIN_CREDENTIALS.username && pass === ADMIN_CREDENTIALS.password) {
        localStorage.setItem("isAdminLoggedIn", "true");
        updateAdminStatusUI();
        const modalEl = document.getElementById('adminLoginModal');
        const modalInstance = bootstrap.Modal.getInstance(modalEl);
        if (modalInstance) modalInstance.hide();
        alert("✅ Admin Authenticated!");
    } else {
        alert("❌ Invalid Credentials!");
    }
}

function logoutAdmin() {
    localStorage.removeItem("isAdminLoggedIn");
    updateAdminStatusUI();
    alert("Logged out from Admin Mode.");
}

function updateAdminStatusUI() {
    const isAdmin = checkAdminAccess();
    const adminBtn = document.getElementById("adminNavBtn");

    if (adminBtn) {
        if (isAdmin) {
            adminBtn.className = "btn btn-outline-light btn-sm me-2";
            adminBtn.innerHTML = `<i class="fa-solid fa-user-shield me-1"></i> Admin (Unlocked)`;
            adminBtn.onclick = logoutAdmin;
        } else {
            adminBtn.className = "btn btn-warning btn-sm me-2 text-dark fw-bold";
            adminBtn.innerHTML = `<i class="fa-solid fa-lock me-1"></i> Admin Login`;
            adminBtn.onclick = () => {
                const modal = new bootstrap.Modal(document.getElementById('adminLoginModal'));
                modal.show();
            };
        }
    }

    document.querySelectorAll(".admin-only").forEach(el => {
        el.style.display = isAdmin ? "inline-block" : "none";
    });
}

document.addEventListener("DOMContentLoaded", updateAdminStatusUI);