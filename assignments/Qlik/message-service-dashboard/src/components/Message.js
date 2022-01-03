import { FaTimes } from 'react-icons/fa'

const Message = ({ message, onDelete, showInfo }) => {
  return (
        <tr style={{background: "#bbbaba"}} onDoubleClick={() => showInfo(message.id)}>
          <td style={{padding: "5px"}}>
            {message.message}{' '}
          </td>
          <td style={{textAlign: "center",padding: "5px"}}>
            <FaTimes
                style={{ color: 'red', cursor: 'pointer' }}
                onClick={() => onDelete(message.id)}
            />
          </td>
        </tr>
  )
}

export default Message