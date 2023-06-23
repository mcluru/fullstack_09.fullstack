import React, { useState } from 'react';
import { Checkbox, 
  IconButton, 
  InputBase, 
  ListItem, 
  ListItemSecondaryAction, 
  ListItemText } from '@mui/material';
import DeleteOutlined from '@mui/icons-material/DeleteOutlined';

const Todo = (props) => {

  const [item, setItem] = useState(props.item)
  const [readOnly, setReadOnly] = useState(false);
  const deleteItem = props.deleteItem;
  const editItem = props.editItem;

  const checkBoxEventHandler = (e) => {
    item.done = e.target.checked;
    editItem(item)
  }

  const turnOffReadOnly = () => {
    setReadOnly(false);  
  }
  const turnOnReadOnly = (e) => {
    if(e.key === 'enter' && readOnly === false) {
      setReadOnly(true);
      editItem(item)
    }
  }

  const editEventHandler = (e) => {
    item.title = e.target.value;
    editItem(item);
    setItem({...item, title: e.target.value});
  }

  const deleteEventHendler = () => {
    deleteItem(item);
  }
 
  return (
    <div>
      <ListItem>
        <Checkbox checked={item.done} onChange={checkBoxEventHandler}/>
        <ListItemText>
          <InputBase 
            inputProps={{
              "arial-label": "nacked",
              readOnly
            }}
            onClick={turnOffReadOnly}
            onKeyDown={turnOnReadOnly}
            onChange={editEventHandler}
            type='text'
            id={item.id}
            name={item.id}
            value={item.title}
            multiline={true}
            fullWidth={true}
          />
        </ListItemText>
        <ListItemSecondaryAction>
          <IconButton aria-label='Todo 삭제'
            onClick={deleteEventHendler}>
            <DeleteOutlined />
          </IconButton>
        </ListItemSecondaryAction>
      </ListItem>
    </div>
  );
};

export default Todo;