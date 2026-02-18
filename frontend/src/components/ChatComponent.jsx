import { useState } from "react"
import '../ChatComponentStyle.css'
function ChatComponent() {
    const [prompt, setPrompt] = useState('')
    const [aiResponse, setAIResponse] = useState('')
    const [inProgress, setInProgress] = useState(false)
    const fetchAIResponse = async () => {
        const payload = {
            'prompt': prompt
        }
        try {
            setInProgress(true)
            const response = await fetch(`http://localhost:7070/ask-ai`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(payload)
            });
            const text = await response.text()
            setAIResponse(text)
            setInProgress(false)
        } catch (error) {
            console.log(error)
            setInProgress(false)
        }
    }
    const changeHandler = (value) => {
        setPrompt(value)
    }
    return (
        <div className="chat-container">
            <h2 className="chat-title">Talk to AI</h2>
            <div className="chat-input-row">
                <input
                    className="chat-input"
                    value={prompt}
                    placeholder="Enter your message here !"
                    onChange={(e) => changeHandler(e.target.value)}
                />
                <button className="chat-button" onClick={fetchAIResponse} disabled={inProgress}>
                    Send
                </button>
            </div>
            {inProgress ? <div className="loading-container">
                <div className="loading-spinner loading-spinner-chat"></div>
                <div>
                    AI is thinking...
                    <span className="loading-dots"></span>
                </div>
            </div> :
                <textarea
                    className="chat-response"
                    defaultValue={aiResponse}
                    readOnly
                    disabled={inProgress}
                />
            }

        </div>


    )
}
export default ChatComponent