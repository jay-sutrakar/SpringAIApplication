import { useState } from "react"
import '../ChatComponentStyle.css'
function ChatComponent() {
    const [prompt, setPrompt] = useState('')
    const [aiResponse, setAIResponse] = useState('')
    const fetchAIResponse = async () => {
        const payload = {
            'prompt': prompt
        }
        try {
            const response = await fetch(`http://localhost:8080/ask-ai`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(payload)
            });
            const text = await response.text()
            console.log(text)
            setAIResponse(text)
        } catch (error) {
            console.log(error)
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
                <button className="chat-button" onClick={fetchAIResponse}>
                    Send
                </button>
            </div>

            <textarea
                className="chat-response"
                defaultValue={aiResponse}
                readOnly
            />
        </div>

    )
}
export default ChatComponent