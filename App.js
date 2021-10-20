import React, { useEffect, useState } from 'react'
import { Alert, FlatList, StyleSheet, Text, TouchableOpacity, View, Image } from 'react-native'
import CustomRNModule from './CustomRNModule';
import VCallImage from './video-call.png'
import axios from 'axios';

export default function App() {
  const [deviceId, setDeviceId] = useState("")
  const [listUser, setListUser] = useState([])
  const [page, setPage] = useState(1)

  useEffect(() => {

    getData()
    // const fetchDeviceID = async () => {
    //   const deviceIDAndroid = await CustomRNModule.getDeviceID();
    //   console.log("deviceIDAndroid ", deviceIDAndroid);
    //   setDeviceId(deviceIDAndroid);
    // }
    // fetchDeviceID();

    return () => {
      // cleanup
    }
  }, [])


  const getData = () => {
    // Call API GET
    axios.get(`https://reqres.in/api/users?page=${page}&per_page=20`)
      .then(result => {
        setListUser([...listUser, ...result.data.data])
        console.log('result : ', listUser)
      })
      .catch(error => console.log('error : ', error))
  };

  const loadMoreData = async () => {
    setPage(page + 1)
    console.log('bottom reach ', page);
  }


  const asking = (name) => {
    Alert.alert(
      "Consultation Meeting Online",
      `Have you made an appointment with ${name}?`,
      [
        {
          text: "not yet",
          onPress: () => console.log("Cancel Pressed"),
          style: "cancel"
        },
        {
          text: "Yes", onPress: () => {
            const options = {
              roomId: `${name}IHealth.com`,
              userInfo: {
                displayName: "User"
              }
            }
            CustomRNModule.joinJitsiMeet(options);
          }
        }
      ]
    );
  }



  const renderItem = ({ key, item }) => (
    <TouchableOpacity
      onPress={
        () => {
          // alert('good')
          asking(item.first_name)
        }
      }
    >
      <View style={styles.contactItemWrapper}>
        <View>
          <Image style={styles.avatar} source={{ uri: item.avatar }} />
          {/* title={item.first_name && item.first_name.slice(0, 2)}
          source={{ uri: item.avatar || "https://i2.wp.com/ui-avatars.com/api//AB/128/532A8C/FFFFFF/?ssl=1", }} */}
        </View>

        <View style={styles.peopleContact}>
          <View style={styles.wrappertextInfo}>
            <View>
              <Text style={[styles.textContact, styles.nameContact]}>{item.first_name} {item.last_name}</Text></View>
            <View>
              <Text style={styles.textContact}>
                {item.email}
              </Text>
            </View>
          </View>
          <View style={styles.wrapperVC}>
            <Image style={styles.vcIcon} source={VCallImage} />
          </View>
        </View>
      </View>
    </TouchableOpacity>
  )

  const keyExtractor = (item, index) => { item.id }

  doSomething = () => {
  }
  console.log('listUser', listUser)

  return (
    <View>
      <View style={styles.wrapperTitle}>
        <Text style={styles.textTitle}>List of Psychiatrists Contact In Asia</Text>
      </View>
      {/* <TouchableOpacity style={styles.defaultButton} onPress={() => { doSomething() }}>
        <Text style={styles.textButton}>Touchable Text</Text>
      </TouchableOpacity> */}
      <View style={styles.wrapperList}>

        {
          listUser.length > 0
            ? (
              <FlatList
                onEndReached={loadMoreData}
                style={styles.listContainer}
                keyExtractor={keyExtractor}
                data={listUser}
                renderItem={renderItem}
              />
            )
            : (
              <View style={styles.emptyContainer}>
                <Text style={styles.emptyText}>Contact data is not available at this time</Text>
              </View>
            )
        }
      </View>
    </View>
  )
}

const styles = StyleSheet.create({
  defaultButton: {
    marginTop: 24,
    width: '80%',
    height: 48,
    alignSelf: 'center',
    alignItems: 'center',
    justifyContent: 'center',
    backgroundColor: 'lavender',
    borderRadius: 24,
    // marginHorizontal:16,
  },
  avatar: {
    borderWidth: 1,
    borderColor: '#0583F2',
    width: 100,
    height: 100,
    borderRadius: 100 / 2,
  },
  textButton: {
    fontSize: 24,
    fontWeight: 'bold'
  },
  nameContact: {
    fontSize: 24,
  },
  textContact: {
    color: '#ffffff',
    fontWeight: 'bold',
  },
  button: {
    borderRadius: 10,
    width: 250,
    height: 50,
    padding: 10,
    margin: 10,
    borderWidth: 2,
    borderColor: "gray",
    justifyContent: "center",
    alignItems: "center",
  },
  vcIcon: {
    width: 24,
    height: 24
  },
  textTitle: {
    fontSize: 28,
    color: '#fff',
    fontWeight: 'bold',
    textAlign: 'center',
  },
  wrapperTitle: {
    backgroundColor: '#0583F2',
    padding: 24,
  },
  wrapperVC: {
    marginTop: 8,
    // paddingHorizontal: 8,
  },
  wrappertextInfo: {
    flexDirection: 'column'
  },
  contactItemWrapper: {
    backgroundColor: '#fff',
    flexDirection: 'row',
    paddingHorizontal: 24,
    paddingVertical: 16,
    borderBottomWidth: 2,
    borderColor: '#0583F2',
  },
  peopleContact: {
    flex: 1,
    padding: 16,
    alignItems: 'flex-start',
    justifyContent: 'center',
    // flexDirection:'row',
    backgroundColor: '#04B2D9',
    borderRadius: 8,
    marginLeft: 16,
  },
  wrapperList: {
    flex: 0,
    // backgroundColor:'grey',
  },
  emptyContainer: {
    marginTop: 100,
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
  },
  container: {
    // marginTop: 48,
    flex: 1,
    backgroundColor: 'grey',
    alignItems: 'center',
    justifyContent: 'center',
  },
})
