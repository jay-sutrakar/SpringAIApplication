import { useState }  from 'react';
import ImageGeneratorComponent from './components/ImageGeneratorComponent';
import RecipeGeneratorComponent from './components/RecipeGeneratorComponent';
import ChatComponent from './components/ChatComponent';

function App() {
  const [activeTab, setActiveTab] = useState('image-generator')
  const handleTabChange = (value) => {
    setActiveTab(value)
  }
  return (
    <div className="App">
      <button onClick={() => handleTabChange('chat')}>Chat</button>
      <button onClick={() => handleTabChange('image-generator')}>Image Generator</button>
      <button onClick={() => handleTabChange('recipe-generator')}>Recipe Generator</button>
      <div>
        {activeTab === 'chat' && <ChatComponent/>}
        {activeTab === 'image-generator' && <ImageGeneratorComponent/>}
        {activeTab === 'recipe-generator' && <RecipeGeneratorComponent/>}
      </div>
    </div>
  );
}

export default App;
