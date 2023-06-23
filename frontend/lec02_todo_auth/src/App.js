import {useEffect, useState} from 'react';
import './App.css';
import { call, signout } from './service/ApiService';
import { List, Paper, Container, AppBar, Toolbar, Grid, Typography, Button } from '@mui/material';
import Todo from './Todo';
import AddTodo from './AddTodo';

function App() {

  const [items, setItems] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    call("/todo", "GET", null)
      .then(response => {
        setItems(response.data);
        setLoading(false)});
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
          <Todo
            key={item.id}
            item={item}
            deleteItem={deleteItem}
            editItem={editItem}
          />
        ))}
      </List>
    </Paper>
  );

  // navigation bar 추가하기
  let navigationBar = (
    <AppBar position="static">
      <Toolbar>
        <Grid justifyContent="space-between" container>
          <Grid item>
            <Typography variant="h6">오늘의 할일</Typography>
          </Grid>
          <Grid item>
            <Button color="inherit" raised onClick={signout}>
              로그아웃
            </Button>
          </Grid>
        </Grid>
      </Toolbar>
    </AppBar>
  );
  

  return (
    <div className="App">
      {navigationBar}
      <Container maxWidth="md">
        <AddTodo addItem={addItem} editItem={editItem} />
        <div className='TodoList'>{todoItems}</div>
      </Container>
    </div>
  );
}

export default App;
