import i0 from "src/assets/img-nopic-0.png"
import i1 from "src/assets/img-nopic-1.png"
import i2 from "src/assets/img-nopic-2.png"
import i3 from "src/assets/img-nopic-3.png"
import i4 from "src/assets/img-nopic-4.png"
import i5 from "src/assets/img-nopic-5.png"

export const getRandomImage = () => {
  const images = [
    i0,
    i1,
    i2,
    i3,
    i4,
    i5
  ]

  const randomIndex = Math.floor(Math.random() * images.length)

  return images[randomIndex]
}