import { useModal } from 'react-hooks-use-modal';

const Popup = () => {
  const [Modal, open, close, isOpen] = useModal('root', {
    preventScroll: true,
    closeOnOverlayClick: false
  });
  return (
      <div>
        <p>Modal is Open? {isOpen ? 'Yes' : 'No'}</p>
        <button onClick={open}>OPEN</button>
        <Modal>
          <div>
            <h1>Title</h1>
            <p>This is a customizable modal.</p>
            <button onClick={close}>CLOSE</button>
          </div>
        </Modal>
      </div>
  );
}

export default Popup