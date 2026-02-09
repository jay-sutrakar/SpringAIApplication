import { useState } from "react";

function ImageGeneratorComponent () {
    const [prompt, setPrompt] = useState('')
   return (
    <div>
        <h2>Image Generator</h2>
        <input
            type='text'
            onChange={e => setPrompt(e.target.value)}
            value={prompt}
            placeholder="Enter the prompt to generate image."
        />
    </div>
   )
}

export default ImageGeneratorComponent;