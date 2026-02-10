import { useState } from "react"

function ChatComponent () {
    const [prompt, setPrompt] = useState('')
    const [aiResponse, setAIResponse] = useState('')
    const fetchAIResponse = async () => {
        const payload = {
            'prompt': prompt
        }
        try {
            const response = await fetch(`http://localhost:8080/ask-ai`, {
                method: 'POST',
                headers : {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(payload)
            });
            const text = await response.text()
            console.log(text)
            setAIResponse(text)
        } catch(error) {
            console.log(error)
        }
     
    }
    const changeHandler = (value) => {
        setPrompt(value)   
    }
    return (
        <div>
            <h2>Talk to AI</h2>
            <input
                value={prompt}
                placeholder="Enter your message here !"
                onChange={(e) => changeHandler(e.target.value)}
            />
            <button onClick={fetchAIResponse}>Send</button>
            <textarea
                defaultValue={aiResponse}
            />
        </div>
    )
}
export default ChatComponent