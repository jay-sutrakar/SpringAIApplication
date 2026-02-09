import { useState } from "react";

function ImageGeneratorComponent () {
    const [prompt, setPrompt] = useState('')
    const [imgSrc, setImageSrc] = useState(null)
    const generateImage = async () => {
        try {
            const response = await fetch('http://localhost:8080/generate-image?prompt=dog-image')
            console.log(response);
            const blob = await response.blob()
            const url = URL.createObjectURL(blob)
            setImageSrc(url)
        } catch (error) {
            console.error(error)
        }
    } 
   return (
    <div>
        <h2>Image Generator</h2>
        <input
            type='text'
            onChange={e => setPrompt(e.target.value)}
            value={prompt}
            placeholder="Enter the prompt to generate image."
        />
        <button onClick={generateImage}>Generate Image</button>
        <div>
            <img src={imgSrc}/>
        </div>
    </div>
   )
}

export default ImageGeneratorComponent;