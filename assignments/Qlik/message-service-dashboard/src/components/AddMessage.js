import { useState } from 'react'

const AddMessage = ({ onAdd }) => {
  const [message, setMessage] = useState('')

  const onSubmit = (e) => {
    e.preventDefault()

    if (!message) {
      alert('Please add a message')
      return
    }

    onAdd({ message: message })

    setMessage('')
  }

  return (
      <form className='add-form' onSubmit={onSubmit}>
        <div className='form-control'>
          <label>Message</label>
          <input
              type='text'
              placeholder='Add message'
              value={message}
              onChange={(e) => setMessage(e.target.value)}
          />
        </div>

        <input type='submit' value='Save Message' className='btn btn-block' />
      </form>
  )
}

export default AddMessage