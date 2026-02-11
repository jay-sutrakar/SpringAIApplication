import { useState } from "react";
import '../ImageComponentStyle.css'

function ImageGeneratorComponent() {
    const [prompt, setPrompt] = useState('')
    const [imgSrc, setImageSrc] = useState(null)
    const [inProgress, setInProgress] = useState(false)
    const generateImage = async () => {
        try {
            setInProgress(true)
            const response = await fetch(`http://localhost:8080/generate-image?prompt=${prompt}`)
            console.log(response);
            const blob = await response.blob()
            const url = URL.createObjectURL(blob)
            setImageSrc(url)
            setInProgress(false)
        } catch (error) {
            console.error(error)
            setInProgress(false)
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
                <button className="image-gen-button" onClick={generateImage} disabled={inProgress}>
                    Generate Image
                </button>
            </div>
            {inProgress ? <div className="loading-container">
                <div className="loading-spinner loading-spinner-image"></div>
                <p className="loading-text">Generating image<span className="loading-dots"></span></p>
            </div> :
                <div className="image-gen-output">
                    {imgSrc ? (
                        <img src={imgSrc} alt="Failed to load image" className="image-gen-img" />
                    ) : (
                        <div className="image-gen-placeholder">
                            Generated image will appear here
                        </div>
                    )}
                </div>
            }
        </div>

    )
}

export default ImageGeneratorComponent;