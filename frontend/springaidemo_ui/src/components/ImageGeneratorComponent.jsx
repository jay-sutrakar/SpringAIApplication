import { useState } from "react";
import '../ImageComponentStyle.css'

function ImageGeneratorComponent() {
    const [prompt, setPrompt] = useState('')
    const [imgSrc, setImageSrc] = useState(null)
    const generateImage = async () => {
        try {
            const response = await fetch(`http://localhost:8080/generate-image?prompt=${prompt}`)
            console.log(response);
            const blob = await response.blob()
            const url = URL.createObjectURL(blob)
            setImageSrc(url)
        } catch (error) {
            console.error(error)
        }
    }
    return (
        <div className="image-gen-container">
            <h2 className="image-gen-title">Image Generator</h2>

            <div className="image-gen-input-row">
                <input
                    className="image-gen-input"
                    type="text"
                    onChange={(e) => setPrompt(e.target.value)}
                    value={prompt}
                    placeholder="Enter the prompt to generate image."
                />
                <button className="image-gen-button" onClick={generateImage}>
                    Generate Image
                </button>
            </div>

            <div className="image-gen-output">
                {imgSrc ? (
                    <img src={imgSrc} alt="Generated" className="image-gen-img" />
                ) : (
                    <div className="image-gen-placeholder">
                        Generated image will appear here
                    </div>
                )}
            </div>
        </div>

    )
}

export default ImageGeneratorComponent;