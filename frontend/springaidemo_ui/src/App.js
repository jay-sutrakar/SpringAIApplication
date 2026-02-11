import { useState }  from 'react';
import ImageGeneratorComponent from './components/ImageGeneratorComponent';
import ChatComponent from './components/ChatComponent';
import './App.css'

function App() {
  const [activeTab, setActiveTab] = useState('chat')
  const handleTabChange = (value) => {
    setActiveTab(value)
  }
  return (
   <div className="app-container">
  <nav className="tab-nav">
    <button 
      className={`tab-button ${activeTab === 'chat' ? 'tab-button--active' : ''}`}
      onClick={() => handleTabChange('chat')}
    >
      Chat
    </button>
    <button 
      className={`tab-button ${activeTab === 'image-generator' ? 'tab-button--active' : ''}`}
      onClick={() => handleTabChange('image-generator')}
    >
      Image Generator
    </button>
  </nav>

  <main className="tab-content">
    {activeTab === 'chat' && <ChatComponent />}
    {activeTab === 'image-generator' && <ImageGeneratorComponent />}
  </main>
</div>

  );
}

export default App;
