// URL publique du backend Spring Boot déployé sur Railway.
const API_BASE_URL = 'https://sport-club-management-production-6f45.up.railway.app';

const apiStatus = document.querySelector('#apiStatus');
const membersList = document.querySelector('#membersList');
const reloadButton = document.querySelector('#reloadMembers');

function getMemberName(member) {
  return member.name || member.fullName || member.full_name || [member.firstName, member.lastName].filter(Boolean).join(' ') || member.username || `Membre #${member.id ?? ''}`;
}

function getMemberDetail(member) {
  return member.email || member.sport || member.role || 'Membre du club';
}

function renderMembers(members) {
  membersList.replaceChildren();
  if (!Array.isArray(members) || members.length === 0) {
    membersList.innerHTML = '<p class="empty-state">Aucun membre à afficher pour le moment. Dès que des membres seront enregistrés, ils apparaîtront ici.</p>';
    return;
  }
  members.slice(0, 12).forEach((member) => {
    const row = document.createElement('article');
    row.className = 'member-item';
    const details = document.createElement('div');
    const name = document.createElement('div');
    name.className = 'member-name';
    name.textContent = getMemberName(member);
    const meta = document.createElement('div');
    meta.className = 'member-meta';
    meta.textContent = getMemberDetail(member);
    details.append(name, meta);
    const badge = document.createElement('span');
    badge.className = 'member-badge';
    badge.textContent = 'Communauté';
    row.append(details, badge);
    membersList.append(row);
  });
}

async function loadMembers() {
  apiStatus.classList.remove('api-error');
  apiStatus.textContent = 'Connexion à l’API en cours…';
  reloadButton.disabled = true;
  try {
    const response = await fetch(`${API_BASE_URL}/api/members`, {
      headers: { Accept: 'application/json' }
    });
    if (!response.ok) throw new Error(`Le serveur a répondu avec le statut ${response.status}.`);
    const data = await response.json();
    const members = Array.isArray(data) ? data : (data.content || data.members || data.data || []);
    renderMembers(members);
    apiStatus.textContent = `Connexion réussie · ${members.length} membre(s) récupéré(s)`;
  } catch (error) {
    apiStatus.classList.add('api-error');
    apiStatus.textContent = 'Impossible de charger les membres depuis l’API.';
    membersList.innerHTML = `<p class="empty-state">${error.message} Vérifie que le backend Railway est en ligne et que CORS autorise le domaine Vercel. Le site peut s’afficher même si cette section ne parvient pas encore à joindre l’API.</p>`;
  } finally {
    reloadButton.disabled = false;
  }
}

reloadButton.addEventListener('click', loadMembers);
document.querySelector('#year').textContent = new Date().getFullYear();
const menuToggle = document.querySelector('#menuToggle');
const mainNav = document.querySelector('#mainNav');
menuToggle.addEventListener('click', () => {
  const open = mainNav.classList.toggle('open');
  menuToggle.setAttribute('aria-expanded', String(open));
  menuToggle.setAttribute('aria-label', open ? 'Fermer le menu' : 'Ouvrir le menu');
});
mainNav.querySelectorAll('a').forEach((link) => link.addEventListener('click', () => {
  mainNav.classList.remove('open');
  menuToggle.setAttribute('aria-expanded', 'false');
}));
loadMembers();
