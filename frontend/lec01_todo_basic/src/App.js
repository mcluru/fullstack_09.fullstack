import {useEffect, useState} from 'react';
import './App.css';
import { call } from './service/AppService';
import { List, Paper, Container } from '@mui/material';
import Todo from './Todo';
import AddTodo from './AddTodo';

function App() {

  const [items, setItems] = useState([
    // {id: "0", title: "새글 등록하기"}
  ]);

  useEffect(() => {
    call("/todo", "GET", null)
      .then(response => setItems(response.data));
  },[])

  const addItem = (item) => {
    call("/todo", "POST", item)
      .then((response) => setItems(response.data))
  }
  
  const editItem = (item) => {
    call("/todo", "PUT", item)
      .then((response) => setItems(response.data))    
  }
  
  const deleteItem = (item) => {
    call("/todo", "DELETE", item)
      .then((response) => setItems(response.data))       
  }
  
  let todoItems = items.length > 0 &&  (
    <Paper style={{margin: 16}}>
      <List>
        {items.map((item) => (
          <Todo key={item.id} item={item} deleteItem={deleteItem} editItem={editItem} />
        ))}
      </List>
    </Paper>
  );

  return (
    <div className="App">
      <Container maxWidth="md">
        <AddTodo addItem={addItem} editItem={editItem} />
        <div className='TodoList'>{todoItems}</div>
      </Container>
    </div>
  );
}

export default App;
