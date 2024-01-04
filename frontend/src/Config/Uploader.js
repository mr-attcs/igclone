export const upload = async (image) => {

    if (image) {
        const data = new FormData();
        data.append("file", image)
        data.append("upload_preset", "instaclone")
        data.append("cloud_name", "dwkzlyldj")


        const res = await fetch("https://api.cloudinary.com/v1_1/dwkzlyldj/image/upload",{
            method: "POST",
            body: data
        })
        const fileData = await res.json();

        console.log("filedata: ", fileData)
        return fileData.url.toString()
    }

}