import './style.css';

interface ScreenInfo {
  id: string;
  title: string;
  icon: string;
  file: string;
  category: 'onboarding' | 'vault' | 'media' | 'settings' | 'states';
}

const screens: ScreenInfo[] = [
  // Onboarding Flow
  { id: 'splash', title: 'Splash Screen', icon: 'shield', file: 'splash-screen.html', category: 'onboarding' },
  { id: 'onboard1', title: 'Onboarding Page 1', icon: 'waving_hand', file: 'onboarding-page-1.html', category: 'onboarding' },
  { id: 'privacy', title: 'Onboarding: Privacy', icon: 'lock', file: 'onboarding-privacy.html', category: 'onboarding' },
  { id: 'setup', title: 'Onboarding: Setup', icon: 'settings_suggest', file: 'onboarding-setup.html', category: 'onboarding' },
  
  // Vault Screens
  { id: 'auth', title: 'Vault Authentication', icon: 'fingerprint', file: 'vault-authentication.html', category: 'vault' },
  { id: 'browser', title: 'Vault Browser', icon: 'folder_open', file: 'vault-browser.html', category: 'vault' },
  { id: 'media-viewer', title: 'Vault Media Viewer', icon: 'image', file: 'vault-media-viewer.html', category: 'vault' },
  { id: 'search', title: 'Search Experience', icon: 'search', file: 'search-experience.html', category: 'vault' },
  
  // Media Players
  { id: 'video-browse', title: 'Video Browser', icon: 'video_library', file: 'video-browser.html', category: 'media' },
  { id: 'video-play', title: 'Video Player', icon: 'play_circle', file: 'video-player.html', category: 'media' },
  { id: 'audio-play', title: 'Audio Player', icon: 'headphones', file: 'audio-player.html', category: 'media' },
  
  // Settings
  { id: 'app-settings', title: 'App Settings', icon: 'settings', file: 'app-settings.html', category: 'settings' },
  { id: 'equalizer', title: 'Equalizer Settings', icon: 'equalizer', file: 'equalizer-settings.html', category: 'settings' },
  { id: 'subtitles', title: 'Subtitle Settings', icon: 'subtitles', file: 'subtitle-settings.html', category: 'settings' },
  { id: 'security-logs', title: 'Security Logs', icon: 'history', file: 'security-logs.html', category: 'settings' },
  
  // States
  { id: 'empty', title: 'Empty State: Vault', icon: 'inventory_2', file: 'empty-state-vault.html', category: 'states' },
  { id: 'lockout', title: 'Error: Security Lockout', icon: 'gpp_bad', file: 'error-security-lockout.html', category: 'states' },
];

const categoryLabels: Record<string, string> = {
  onboarding: 'Onboarding',
  vault: 'Vault',
  media: 'Media Players',
  settings: 'Settings',
  states: 'States & Errors',
};

const categoryOrder = ['onboarding', 'vault', 'media', 'settings', 'states'];

function renderNavHub(): string {
  let gridHTML = '';
  
  for (const cat of categoryOrder) {
    const catScreens = screens.filter(s => s.category === cat);
    if (catScreens.length === 0) continue;
    
    gridHTML += `<div class="section-label">${categoryLabels[cat]}</div>`;
    gridHTML += `<div class="screen-grid">`;
    
    for (const screen of catScreens) {
      gridHTML += `
        <div class="screen-card" data-screen-id="${screen.id}" onclick="window.__openScreen('${screen.id}')">
          <div class="card-icon-area">
            <span class="material-symbols-outlined">${screen.icon}</span>
          </div>
          <div class="card-label">${screen.title}</div>
        </div>
      `;
    }
    
    gridHTML += `</div>`;
  }

  return `
    <div class="nav-hub">
      <div class="nav-header">
        <div class="brand">
          <span class="material-symbols-outlined shield-icon">shield</span>
          <h1>CIPHER</h1>
        </div>
        <p class="subtitle">Your Media, Your Secret</p>
      </div>
      ${gridHTML}
    </div>
  `;
}

function renderScreenViewer(screen: ScreenInfo): string {
  return `
    <div class="screen-viewer">
      <div class="viewer-topbar">
        <button class="back-btn" onclick="window.__goBack()">
          <span class="material-symbols-outlined">arrow_back</span>
        </button>
        <span class="screen-title">${screen.title}</span>
      </div>
      <iframe class="viewer-frame" src="/screens/${screen.file}" sandbox="allow-scripts allow-same-origin"></iframe>
    </div>
  `;
}

// Global navigation functions
declare global {
  interface Window {
    __openScreen: (id: string) => void;
    __goBack: () => void;
  }
}

const app = document.getElementById('app')!;

function showHub() {
  app.innerHTML = renderNavHub();
}

function showScreen(id: string) {
  const screen = screens.find(s => s.id === id);
  if (screen) {
    app.innerHTML = renderScreenViewer(screen);
  }
}

window.__openScreen = (id: string) => {
  showScreen(id);
  history.pushState({ screen: id }, '', `#${id}`);
};

window.__goBack = () => {
  history.back();
};

window.addEventListener('popstate', (e) => {
  if (e.state?.screen) {
    showScreen(e.state.screen);
  } else {
    showHub();
  }
});

// Initial render
showHub();
