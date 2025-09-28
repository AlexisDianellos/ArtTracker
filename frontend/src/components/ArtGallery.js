import React,{useState,useEffect} from 'react';
import api from '../api/axiosConfig';
import DrawingBoard from './DrawingBoard';

const ArtGallery = () => {
  const [art,setArt]=useState([]);
  const [page,setPage]=useState(0);
  const[size,setSize]=useState(3);
  const [showArtPopup,setShowArtPopup]=useState(false);
  const [message,setMessage]=useState("");
  const [postForm, setPostForm] = useState({
    name: "",
    description: "",
  });
  const[imageFile,setImageFile]=useState(null);

  useEffect(() => {
    const fetchArt = async () => {
      try {
        const res = await api.get(`/art/?page=${page}&size=${size}`);
        setArt(res.data.content||[]);
      } catch (err) {
        console.error("Error fetching art:", err);
        setArt([]);
    }
  }
    fetchArt();
  }, [page,size]);

  const handleChange = (e) => {
    setPostForm((prev) => ({ ...prev, [e.target.name]: e.target.value }));
  };

  const handleMore = (e)=>{
    setSize((prev) => prev + 3);
  }

  const handleSubmit = async (e) => {
    e.preventDefault();
    const formData = new FormData();
    formData.append("art", new Blob([JSON.stringify(postForm)], { type: "application/json" }));
    formData.append("image", imageFile);

    try {
      await api.post("/art/create", formData);
        setMessage("Art created");

        setPage(0);

        setShowArtPopup(false);
        setPostForm({ name: "", description: "" });
        setImageFile(null);
    } catch (err) {
            const data = err?.response?.data;
      const text =
        typeof data === "string" ? data : data?.error || data?.message;
      setMessage(text || "Something went wrong creating art");
    }
  };
  

  return (
  <div className=''>
      <h2 >Art Gallery</h2>
      <button onClick={()=>{setShowArtPopup(true)}}>Create Art</button>
      {art.length === 0 ? (
        <p>No art found.</p>
      ) : (
        <div style={{ display: "grid", gridTemplateColumns: "repeat(3, 1fr)", gap: "20px" }}>
          {art.map((item) => (
            <div
              key={item.id}
              style={{ border: "1px solid #ccc", padding: "10px", borderRadius: "5px" }}
            >
              <img src={item.imageUrl} alt={item.name} style={{ width: "100%", borderRadius: "5px" }} />
              <h3>{item.name}</h3>
              <p>{item.description}</p>
              <p><strong>By:</strong> {item.username}</p>
            </div>
          ))}
        </div>
      )}
      <button onClick={handleMore}>more</button>
 {showArtPopup && (
        <form onSubmit={handleSubmit}className=''>
          <div
            style={{
              position: "fixed",
              top: 0,
              left: 0,
              right: 0,
              bottom: 0,
              background: "rgba(0,0,0,0.5)",
              display: "flex",
              alignItems: "center",
              justifyContent: "center",
            }}
          >
            <div
              style={{
                background: "white",
                padding: "20px",
                borderRadius: "10px",
                minWidth: "300px",
              }}
            >
              <h3 className='text-center text-2xl font-bold'>Create New Art</h3>
              <input
                name="name"
                placeholder="name"
                value={postForm.name}
                onChange={handleChange}
              />
              <DrawingBoard onSave={(blob) => setImageFile(new File([blob], "drawing.png", { type: "image/png" }))} />
              <input
                name="description"
                placeholder="description"
                value={postForm.description}
                onChange={handleChange}
              />

              <button onClick={() => setShowArtPopup(false)}>Close</button>
              <button type='submit'>Submit</button>
            </div>
            
          </div>
        </form>
      )}
    </div>
  )
}

export default ArtGallery